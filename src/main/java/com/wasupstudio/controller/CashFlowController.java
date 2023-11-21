package com.wasupstudio.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.wasupstudio.constant.ProjectConstant;
import com.wasupstudio.constant.ProjectConstant.OrderStatus;
import com.wasupstudio.exception.ResultGenerator;
import com.wasupstudio.model.CashFlowData;
import com.wasupstudio.model.CashFlowReturnData;
import com.wasupstudio.model.Result;
import com.wasupstudio.model.dto.CashFlowReturnDataDTO;
import com.wasupstudio.model.dto.OrderDTO;
import com.wasupstudio.model.dto.OrderDTO.OrderItemDTO;
import com.wasupstudio.model.entity.MemberEntity;
import com.wasupstudio.model.entity.OrderEntity;
import com.wasupstudio.model.entity.OrderItemEntity;
import com.wasupstudio.model.entity.ProductEntity;
import com.wasupstudio.service.MemberService;
import com.wasupstudio.service.OrderItemService;
import com.wasupstudio.service.OrderService;
import com.wasupstudio.service.ProductService;
import com.wasupstudio.util.CashFlowUtils;
import com.wasupstudio.util.JwtUtils;
import com.wasupstudio.util.MailUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.wasupstudio.constant.ProjectConstant.APIStatus.API_RESPONSE_IS_SUCCESS;


@Api(tags = "金流相關 API")
@RestController
@RequestMapping("/api/cash")
@Slf4j
public class CashFlowController {

    @Value("${newebpay.notifyURL}")
    private String notifyURL; //當前配置文件

    /* 正式 */
    @Value("${newebpay.hashKey}")
    private String hashKey;
    @Value("${newebpay.hashIV}")
    private String hashIV;
    @Value("${newebpay.merchantID}")
    private String merchantID;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    MemberService memberService;
    @Autowired
    ProductService productService;

    @ApiOperation("取得藍新加密資料(一般)/建立訂單")
    @PostMapping(value = "/order")
    @Transactional
    protected Result createOrderList(@RequestBody OrderDTO orderDTO) throws Exception {
        MemberEntity member = memberService.getAdminByEmail(orderDTO.getEmail());

        log.info("orderDTO = " + orderDTO);
        List<String> productIds = orderDTO.getProducts().stream()
                .map(OrderItemDTO::getProductId)
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

        long orderId = System.currentTimeMillis() / 1000; //TODO 待確定訂單編號規則

        saveOrder(member, orderDTO, totalPrice, orderId);

        saveOrderItem(orderDTO, productEntityMap, orderId);

        // 前端頁面會送來未加密過的資訊，利用這個API把資訊加密之後送到三方
        return ResultGenerator.genSuccessResult(getCashFlowData(member, totalPrice, orderId));
    }

    @ApiOperation("藍新callback方法")
    @PostMapping(value = "/callback")
    protected Result getReturnData(@ModelAttribute CashFlowReturnDataDTO cashFlowReturnDataDTO)
            throws Exception {
        log.info("callback init result:{}", cashFlowReturnDataDTO);

        // 接收三方收到的訊息然後解密處理實作callback方法
        CashFlowUtils cashFlowUtils = new CashFlowUtils();
        String decrypt = cashFlowUtils.decrypt(cashFlowReturnDataDTO.getTradeInfo(), hashKey, hashIV);
        log.info("callback result:{}", decrypt);
        Gson gson = new Gson();
        CashFlowReturnData data = gson.fromJson(decrypt, CashFlowReturnData.class);

        // 更改交易裝態
        updateOrderStatus(data);

        // 發送信件給USER
        if (API_RESPONSE_IS_SUCCESS.equals(data.getStatus())) {
            MailUtil.sendMail(ProjectConstant.MailType.LICENSING,
                    String.valueOf(Objects.requireNonNull(JwtUtils.getMember()).getId()),
                    Objects.requireNonNull(JwtUtils.getMember()).getEmail()); //TODO 增加寄出授權碼
        }
        log.info("發送交易成功通知信給客戶, {}", decrypt);
        return ResultGenerator.genSuccessResult("交易成功");
    }

    private static JsonObject toJson(String input){

        String[] keyValuePairs = input.split("&");

        // Create a JSON object to store the data
        JsonObject jsonObject = new JsonObject();

        for (String pair : keyValuePairs) {
            // Split each key-value pair by "="
            String[] parts = pair.split("=");
            if (parts.length == 2) {
                // Add the key-value pair to the JSON object
                jsonObject.addProperty(parts[0], parts[1]);
            }
        }
        // Print the JSON object
        System.out.println(jsonObject.toString());
        return jsonObject;
    }

    public static void main(String[] args) {
        String json = "{\"Status\":\"SUCCESS\",\"Message\":\"\\u6388\\u6b0a\\u6210\\u529f\",\"Result\":{\"MerchantID\":\"MS148818392\",\"Amt\":1350,\"TradeNo\":\"23110811063750643\",\"MerchantOrderNo\":\"SW_1699412782\",\"RespondType\":\"JSON\",\"IP\":\"60.251.53.109\",\"EscrowBank\":\"HNCB\",\"PaymentType\":\"CREDIT\",\"RespondCode\":\"00\",\"Auth\":\"215465\",\"Card6No\":\"400022\",\"Card4No\":\"1111\",\"Exp\":\"3512\",\"AuthBank\":\"KGI\",\"TokenUseStatus\":0,\"InstFirst\":0,\"InstEach\":0,\"Inst\":0,\"ECI\":\"\",\"PayTime\":\"2023-11-08 11:06:37\",\"PaymentMethod\":\"CREDIT\"}}\n";
        System.out.println(json);
        Gson gson = new Gson();
        CashFlowReturnData data = gson.fromJson(json, CashFlowReturnData.class);
        System.out.println(data.getStatus());
        System.out.println(data.getMessage());
        System.out.println(data.getResult());
    }
    private void updateOrderStatus(CashFlowReturnData data) {
        String status = data.getStatus();
        CashFlowReturnData.Result result = data.getResult();
        OrderEntity orderEntity = new OrderEntity();
        String merchantOrderNo = result.getMerchantOrderNo().replace("SW_", "");
        orderEntity.setOrderId(Long.parseLong(merchantOrderNo)); //移除 SW_
        orderEntity.setUpdateTime(new Date());
        log.info("金流 callback data:{}", data);
        if (API_RESPONSE_IS_SUCCESS.equals(status)) {
            orderEntity.setStatus(OrderStatus.SUCCESS);
        } else {
            orderEntity.setStatus(OrderStatus.FAIL);
        }
        orderService.updateData(orderEntity);
    }


    private CashFlowData getCashFlowData(MemberEntity member, BigDecimal totalPrice, long orderId) throws Exception {
        CashFlowUtils cashFlowUtils = new CashFlowUtils();
        Map<String, Object> params = new TreeMap<>();
        params.put("MerchantID", merchantID);
        params.put("RespondType", "JSON");
        params.put("TimeStamp", String.valueOf(orderId));
        params.put("Version", "2.0");
        params.put("MerchantOrderNo", "SW_" + orderId);
        params.put("Amt", totalPrice.intValueExact());
        params.put("ItemDesc", member.getId() + ":" + "sw" + orderId); //商品資訊
        params.put("Email", member.getEmail()); //TODO 改從token來
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

    private void saveOrder(MemberEntity member, OrderDTO orderDTO, BigDecimal totalPrice, long orderId) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderId(orderId);
        orderEntity.setUserId(Objects.requireNonNull(member.getId()));
        orderEntity.setRecipient(orderDTO.getRecipient());
        orderEntity.setPhone(orderDTO.getPhone());
        orderEntity.setAddress(orderDTO.getAddress());
        orderEntity.setTotalPrice(totalPrice);
        orderEntity.setStatus(OrderStatus.UNDONE);
        orderEntity.setCreateTime(new Date());
        orderEntity.setUpdateTime(new Date());
        orderService.save(orderEntity);
    }
}
