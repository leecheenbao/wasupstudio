package com.wasupstudio.controller;

import static com.wasupstudio.constant.ProjectConstant.APIStatus.API_RESPONSE_IS_SUCCESS;

import com.google.gson.Gson;
import com.wasupstudio.constant.ProjectConstant;
import com.wasupstudio.constant.ProjectConstant.OrderStatus;
import com.wasupstudio.exception.ResultGenerator;
import com.wasupstudio.model.CashFlowData;
import com.wasupstudio.model.CashFlowReturnData;
import com.wasupstudio.model.Result;
import com.wasupstudio.model.dto.CashFlowReturnDataDTO;
import com.wasupstudio.model.dto.OrderDTO;
import com.wasupstudio.model.dto.OrderDTO.OrderItemDTO;
import com.wasupstudio.model.entity.OrderEntity;
import com.wasupstudio.model.entity.OrderItemEntity;
import com.wasupstudio.model.entity.ProductEntity;
import com.wasupstudio.service.OrderItemService;
import com.wasupstudio.service.OrderService;
import com.wasupstudio.service.ProductService;
import com.wasupstudio.util.CashFlowUtils;
import com.wasupstudio.util.JwtUtils;
import com.wasupstudio.util.MailUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Api(tags = "金流相關 API")
@RestController
@RequestMapping("/api/cash")
@Slf4j
public class CashFlowController {

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
  ProductService productService;

  @ApiOperation("取得藍新加密資料(一般)/建立訂單")
  @PostMapping(value = "/order")
  @Transactional
  protected Result createOrderList(@RequestBody OrderDTO orderDTO) throws Exception {
    System.out.println("orderDTO = " + orderDTO);
    List<String> productIds = orderDTO.getProducts().stream()
        .map(OrderItemDTO::getProductId)
        .collect(Collectors.toList());
    List<ProductEntity> productEntities = productService.findByIds(productIds);
    Map<Long, ProductEntity> productEntityMap = productEntities.stream()
        .collect(Collectors.toMap(ProductEntity::getProductId, Function.identity()));
    //根據productEntities過濾出products中的productId並找到其價格，乘上products中的數量，並加總
    BigDecimal totalPrice = orderDTO.getProducts().stream()
        .map(item -> productEntities.stream()
            .filter(productEntity -> productEntity.getProductId()
                .equals(Long.valueOf(item.getProductId()))).findFirst()
            .map(productEntity -> productEntity.getPrice()
                .multiply(BigDecimal.valueOf(item.getQuantity())))
            .orElseThrow(RuntimeException::new))
        .reduce(BigDecimal.ZERO, BigDecimal::add);

    long orderId = System.currentTimeMillis() / 1000; //TODO 待確定訂單編號規則

    saveOrder(orderDTO, totalPrice, orderId);

    saveOrderItem(orderDTO, productEntityMap, orderId);

    // 前端頁面會送來未加密過的資訊，利用這個API把資訊加密之後送到三方
    return ResultGenerator.genSuccessResult(getCashFlowData(totalPrice, orderId));
  }

  @ApiOperation("藍新callback方法")
  @PostMapping(value = "/callback")
  protected Result getReturnData(@ModelAttribute CashFlowReturnDataDTO cashFlowReturnDataDTO)
      throws Exception {
    //接收三方收到的訊息然後解密處理實作callback方法
    CashFlowUtils cashFlowUtils = new CashFlowUtils();
    String decrypt = cashFlowUtils.decrypt(cashFlowReturnDataDTO.getTradeInfo(), hashKey, hashIV);
    CashFlowReturnData data = new Gson().fromJson(decrypt, CashFlowReturnData.class);
    updateOrderStatus(data);
    if (API_RESPONSE_IS_SUCCESS.equals(data.getStatus())) {
      MailUtil.sendMail(ProjectConstant.MailType.LICENSING,
          String.valueOf(Objects.requireNonNull(JwtUtils.getMember()).getId()),
          Objects.requireNonNull(JwtUtils.getMember()).getEmail()); //TODO 增加寄出授權碼
    }
    log.info("callback result, {}", decrypt);
    return ResultGenerator.genSuccessResult(decrypt);
  }

  private void updateOrderStatus(CashFlowReturnData data) {
    String status = data.getStatus();
    CashFlowReturnData.Result result = data.getResult();
    OrderEntity orderEntity = new OrderEntity();
    orderEntity.setOrderId(Long.parseLong(result.getMerchantOrderNo().substring(2))); //移除 SW_
    orderEntity.setUpdateTime(
        Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
    if (API_RESPONSE_IS_SUCCESS.equals(status)) {
      orderEntity.setStatus(String.valueOf(OrderStatus.SUCCESS));
    } else {
      orderEntity.setStatus(String.valueOf(OrderStatus.FAIL));
    }
    orderService.updateData(orderEntity);
  }


  private CashFlowData getCashFlowData(BigDecimal totalPrice, long orderId) throws Exception {
    CashFlowUtils cashFlowUtils = new CashFlowUtils();
    Map<String, Object> params = new TreeMap<>();
    params.put("MerchantID", merchantID);
    params.put("RespondType", "JSON");
    params.put("TimeStamp", String.valueOf(orderId));
    params.put("Version", "2.0");
    params.put("MerchantOrderNo", "SW_" + orderId);
    params.put("Amt", totalPrice.intValueExact());
    params.put("ItemDesc",
        Objects.requireNonNull(JwtUtils.getMember()).getId() + ":" + "sw" + orderId); //商品資訊
    params.put("Email", "a3583798@gmail.com"); //TODO 改從token來
//    params.put("Email", Objects.requireNonNull(JwtUtils.getMember()).getEmail());

    params.put("NotifyURL",
        "https://dbd4-2001-b011-6c03-9aed-e4c7-482e-87d8-7887.ngrok-free.app/wasupstudio/api/cash/callback");
//    params.put("ReturnURL",
//        "https://dbd4-2001-b011-6c03-9aed-e4c7-482e-87d8-7887.ngrok-free.app/wasupstudio/api/cash/callback");
//        "https://webhook.site/860d9743-b740-493c-b0b1-8f75ee7184d5");

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

  private void saveOrder(OrderDTO orderDTO, BigDecimal totalPrice, long orderId) {
    OrderEntity orderEntity = new OrderEntity();
    orderEntity.setOrderId(orderId);
    orderEntity.setUserId(Objects.requireNonNull(JwtUtils.getMember()).getId());
    orderEntity.setRecipient(orderDTO.getRecipient());
    orderEntity.setPhone(orderDTO.getPhone());
    orderEntity.setAddress(orderDTO.getAddress());
    orderEntity.setTotalPrice(totalPrice);
    orderEntity.setStatus(String.valueOf(OrderStatus.UNDONE));
    Date date = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
    orderEntity.setCreateTime(date);
    orderEntity.setUpdateTime(date);
    orderService.save(orderEntity);

  }
}
