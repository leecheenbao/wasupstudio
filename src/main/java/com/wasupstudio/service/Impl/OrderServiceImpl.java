package com.wasupstudio.service.Impl;

import com.google.gson.Gson;
import com.wasupstudio.constant.BaseRedisKeyConstant;
import com.wasupstudio.constant.ProjectConstant;
import com.wasupstudio.mapper.OrderMapper;
import com.wasupstudio.model.BasePageInfo;
import com.wasupstudio.model.CashFlowData;
import com.wasupstudio.model.CashFlowReturnData;
import com.wasupstudio.model.dto.CashFlowReturnDataDTO;
import com.wasupstudio.model.dto.LicenseDTO;
import com.wasupstudio.model.dto.OrderDTO;
import com.wasupstudio.model.dto.OrderSearchDTO;
import com.wasupstudio.model.entity.LicenseEntity;
import com.wasupstudio.model.entity.OrderEntity;
import com.wasupstudio.model.entity.OrderItemEntity;
import com.wasupstudio.model.entity.ProductEntity;
import com.wasupstudio.model.query.OrderQuery;
import com.wasupstudio.model.vo.LicenseMailVo;
import com.wasupstudio.service.*;
import com.wasupstudio.util.CashFlowUtils;
import com.wasupstudio.util.MailUtil;
import com.wasupstudio.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.wasupstudio.constant.ProjectConstant.APIStatus.API_RESPONSE_IS_SUCCESS;

@Slf4j
@Service
public class OrderServiceImpl extends AbstractService<OrderEntity> implements OrderService {
    @Value("${newebpay.notifyURL}")
    private String notifyURL; //當前配置文件
    @Value("${newebpay.hashKey}")
    private String hashKey;
    @Value("${newebpay.hashIV}")
    private String hashIV;
    @Value("${newebpay.merchantID}")
    private String merchantID;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    ProductService productService;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    LicenseService licenseService;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public OrderEntity findOne(Long id) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderId(id);
        return orderMapper.selectOne(orderEntity);
    }

    @Override
    public List<OrderQuery> findOrderDetail(Long orderId) {
        return orderMapper.getOrderDetail(orderId);
    }

    @Override
    public BasePageInfo findAllData() {
        List<OrderEntity> list = orderMapper.selectAll();
        BasePageInfo basePageInfo = new BasePageInfo<>();
        basePageInfo.setList(list);
        basePageInfo.setTotal(list.size());
        return basePageInfo;
    }

    @Override
    public BasePageInfo findByCondiction(OrderSearchDTO orderSearchDTO) {
        if (orderSearchDTO.getOrderId() != null) {
            String orderId = orderSearchDTO.getOrderId().toUpperCase();
            orderId = orderId.replace("SW_", "");
            orderSearchDTO.setOrderId(orderId);
        }
        List<OrderEntity> list = orderMapper.findByCondiction(orderSearchDTO);
        BasePageInfo basePageInfo = new BasePageInfo<>();
        basePageInfo.setList(list);
        basePageInfo.setTotal(list.size());
        return basePageInfo;
    }

    @Override
    public void updateData(OrderEntity orderEntity) {
        this.update(orderEntity);
    }

    @Override
    @Transactional
    public CashFlowData creatOrder(OrderDTO orderDTO) {
        try {
            log.info("orderDTO = " + orderDTO);
            List<String> productIds = orderDTO.getProducts().stream()
                    .map(OrderDTO.OrderItemDTO::getProductId)
                    .collect(Collectors.toList());
            List<ProductEntity> productEntities = productService.findByIds(productIds);
            Map<Long, ProductEntity> productEntityMap = productEntities.stream()
                    .collect(Collectors.toMap(ProductEntity::getProductId, Function.identity()));

            //根據productEntities過濾出products中的productId並找到其價格，乘上products中的數量，並加總
            BigDecimal totalPrice = orderDTO.getProducts().stream()
                    .map(item -> productEntities.stream()
                            .filter(productEntity -> productEntity.getProductId().equals(Long.valueOf(item.getProductId()))).findFirst()
                            .map(productEntity -> productEntity.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                            .orElseThrow(RuntimeException::new))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            long orderId = System.currentTimeMillis() / 1000;

            saveOrder(orderDTO, totalPrice, orderId);

            saveOrderItem(orderDTO, productEntityMap, orderId);

            return getCashFlowData(orderDTO.getEmail(), totalPrice, orderId);
        } catch (Exception e) {

        }

        return null;
    }

    @Override
    @Transactional
    public Boolean orderCallBack(CashFlowReturnDataDTO cashFlowReturnDataDTO) {
        try {
            // 接收三方收到的訊息然後解密處理實作callback方法
            CashFlowUtils cashFlowUtils = new CashFlowUtils();
            String decrypt = cashFlowUtils.decrypt(cashFlowReturnDataDTO.getTradeInfo(), hashKey, hashIV);
            log.info("callback result:{}", decrypt);

            Gson gson = new Gson();
            CashFlowReturnData data = gson.fromJson(decrypt, CashFlowReturnData.class);
            Long merchantOrderNo = Long.valueOf(data.getResult().getMerchantOrderNo().replace("SW_", ""));
            // 更改交易裝態
            updateOrderStatus(data);
            // 取得訂單資訊
            OrderEntity order = orderService.findOne(merchantOrderNo);
            LicenseDTO licenseDTO = LicenseDTO.builder()
                    .startTime(new Date())
                    .generate(order.getAddress())
                    .build();

            String redisKey = String.format(BaseRedisKeyConstant.ORDER_LICENCE, order.getOrderId());

            String license = redisUtil.getKey(redisKey);
            if (license == null) {
                LicenseEntity licenseEntity = licenseService.genLicense(licenseDTO);
                redisUtil.setKey(redisKey, licenseEntity.getLicenseKey());
                // 把訂單資訊發送信件給USER
                if (API_RESPONSE_IS_SUCCESS.equals(data.getStatus())) {
                    LicenseMailVo vo = new LicenseMailVo();
                    vo.setAmount(order.getTotalPrice());
                    vo.setEmail(order.getAddress());
                    vo.setOrderId(order.getOrderId());
                    vo.setName(order.getRecipient());
                    vo.setLicense(licenseEntity.getLicenseKey());
                    MailUtil.sendMail(vo, order.getAddress()); //TODO 增加寄出授權碼
                }
                log.info("發送交易成功通知信給客戶, {}", decrypt);
                productService.findAllData();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    private void saveOrder(OrderDTO orderDTO, BigDecimal totalPrice, long orderId) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderId(orderId);
        orderEntity.setRecipient(orderDTO.getRecipient());
        orderEntity.setPhone(orderDTO.getPhone());
        orderEntity.setAddress(orderDTO.getEmail());
        orderEntity.setTotalPrice(totalPrice);
        orderEntity.setStatus(ProjectConstant.OrderStatus.UNDONE);
        orderEntity.setCreateTime(new Date());
        orderEntity.setUpdateTime(new Date());
        orderService.save(orderEntity);
    }

    private void saveOrderItem(OrderDTO orderDTO, Map<Long, ProductEntity> productEntityMap,
                               long orderId) {
        List<OrderItemEntity> orderItemEntities = orderDTO.getProducts().stream().map(orderItemDTO -> {
            OrderItemEntity orderItemEntity = new OrderItemEntity();
            orderItemEntity.setOrderId(orderId);
            Long productId = Long.valueOf(orderItemDTO.getProductId());
            orderItemEntity.setProductId(productId);
            orderItemEntity.setPrice(productEntityMap.get(productId).getPrice());
            orderItemEntity.setQuantity(orderItemDTO.getQuantity());
            System.out.println("orderItemEntity = " + orderItemEntity);
            return orderItemEntity;
        }).collect(Collectors.toList());
        orderItemService.save(orderItemEntities);
    }

    private CashFlowData getCashFlowData(String email, BigDecimal totalPrice, long orderId) throws Exception {
        CashFlowUtils cashFlowUtils = new CashFlowUtils();
        Map<String, Object> params = new TreeMap<>();
        params.put("MerchantID", merchantID);
        params.put("RespondType", "JSON");
        params.put("TimeStamp", String.valueOf(orderId));
        params.put("Version", "2.0");
        params.put("MerchantOrderNo", "SW_" + orderId);
        params.put("Amt", totalPrice.intValueExact());
        params.put("ItemDesc", "sw" + orderId); //商品資訊
        params.put("Email", email);
        params.put("NotifyURL", notifyURL);

        String dataInfo = cashFlowUtils.getDataInfo(params);
        String tradeInfo = cashFlowUtils.encrypt(dataInfo, hashKey, hashIV);
        String tradeSha = cashFlowUtils.getTradeSha(tradeInfo, hashKey, hashIV);

        CashFlowData cashFlowData = new CashFlowData();
        cashFlowData.setMerchantID_(merchantID);
        cashFlowData.setVersion("2.0");
        cashFlowData.setTradeInfo(tradeInfo);
        cashFlowData.setTradeSha(tradeSha);
        return cashFlowData;
    }

    private void updateOrderStatus(CashFlowReturnData data) {
        String status = data.getStatus();
        CashFlowReturnData.Result result = data.getResult();
        OrderEntity orderEntity = new OrderEntity();
        String merchantOrderNo = result.getMerchantOrderNo().replace("SW_", "");
        orderEntity.setOrderId(Long.parseLong(merchantOrderNo)); //移除 SW_
        orderEntity.setUpdateTime(new Date());
        log.info("金流 callback data:{}", data);
        List<OrderQuery> orderQuery = orderService.findOrderDetail(Long.valueOf(merchantOrderNo));
        for (OrderQuery query : orderQuery) {
            Long productId = query.getProductId();
            Integer quantity = query.getQuantity();
            if (query.getStatus().equals(ProjectConstant.OrderStatus.UNDONE)) {
                productService.subProduct(productId, quantity);
            }
        }

        if (API_RESPONSE_IS_SUCCESS.equals(status)) {
            orderEntity.setStatus(ProjectConstant.OrderStatus.SUCCESS);
        } else {
            orderEntity.setStatus(ProjectConstant.OrderStatus.FAIL);
        }
        orderService.updateData(orderEntity);

    }
}
