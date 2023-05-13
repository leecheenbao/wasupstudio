package com.wasupstudio.controller;

import com.wasupstudio.constant.ProjectConstant.OrderStatus;
import com.wasupstudio.enums.ResultCode;
import com.wasupstudio.exception.ResultGenerator;
import com.wasupstudio.model.BasePageInfo;
import com.wasupstudio.model.CashFlowData;
import com.wasupstudio.model.CashFlowReturnData;
import com.wasupstudio.model.Result;
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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.UnsupportedEncodingException;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Api(tags = "金流相關 API")
@RestController
@RequestMapping("/api/cash")
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
  protected CashFlowData createOrderList(OrderDTO orderDTO) throws Exception {

    List<String> productIds = orderDTO.getProducts().stream()
        .map(OrderItemDTO::getProductId)
        .collect(Collectors.toList());
    List<ProductEntity> products = productService.findByIds(productIds);
    Map<Long, ProductEntity> productEntityMap = products.stream()
        .collect(Collectors.toMap(ProductEntity::getProductId, Function.identity()));
    BigDecimal totalPrice = products.stream()
        .map(item -> {
          ProductEntity product = productService.findOne(item.getProductId());
          BigDecimal price = product.getPrice();
          int quantity = item.getQuantity();
          return price.multiply(BigDecimal.valueOf(quantity));
        })
        .reduce(BigDecimal.ZERO, BigDecimal::add);
    long orderId = System.currentTimeMillis() / 1000; //TODO 待確定訂單編號規則

    OrderEntity orderEntity = new OrderEntity();
    orderEntity.setOrderId(orderId);
    orderEntity.setUserId(Objects.requireNonNull(JwtUtils.getMember()).getId());
    orderEntity.setRecipient(orderDTO.getRecipient());
    orderEntity.setPhone(orderDTO.getPhone());
    orderEntity.setAddress(orderDTO.getAddress());
    orderEntity.setTotalPrice(totalPrice);
    orderEntity.setStatus(String.valueOf(OrderStatus.UNDONE));
    orderEntity.setCreateTime(
        Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
    orderService.save(orderEntity);

    List<OrderItemEntity> orderItemEntities = orderDTO.getProducts().stream().map(orderItemDTO -> {
      OrderItemEntity orderItemEntity = new OrderItemEntity();
      orderItemEntity.setOrderId(orderId);
      Long productId = orderItemEntity.getProductId();
      orderItemEntity.setProductId(productId);
      orderItemEntity.setPrice(productEntityMap.get(productId).getPrice());
      orderItemEntity.setQuantity(orderItemEntity.getQuantity());
      return orderItemEntity;
    }).collect(Collectors.toList());
    orderItemService.save(orderItemEntities);

    // 前端頁面會送來未加密過的資訊，利用這個API把資訊加密之後送到三方
    CashFlowUtils cashFlowUtils = new CashFlowUtils();
    Map<String, Object> params = new TreeMap<>();
    params.put("MerchantID", merchantID);
    params.put("RespondType", "JSON");
    params.put("TimeStamp", String.valueOf(orderId));
    params.put("Version", "2.0");
    params.put("MerchantOrderNo", "sw" + orderId);
    params.put("Amt", totalPrice);
    params.put("ItemDesc",
        Objects.requireNonNull(JwtUtils.getMember()).getId() + ":" + "sw" + orderId);
    params.put("Email", "a3583798@gmail.com"); //TODO 改從token來
//    params.put("NotifyURL", "https://webhook.site/91346899-a36b-4fc6-915b-7b397a63e213");
    params.put("ReturnURL",
        "https://c244-2001-b011-5c10-37d4-a937-1625-1e85-d80d.ngrok-free.app/wasupstudio/api/cash/callback");

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

  @ApiOperation("藍新callback方法")
  @PostMapping(value = "/callback")
  protected Result getReturnData(CashFlowReturnData cashFlowReturnData) {
    System.out.println("cashFlowReturnData = " + cashFlowReturnData);
    BasePageInfo pageInfo = new BasePageInfo<>();
    //接收三方收到的訊息然後解密處理實作callback方法
    CashFlowUtils cashFlowUtils = new CashFlowUtils();
    try {
      Map<String, String> blueNewData = cashFlowUtils.getBlueNewData(
          cashFlowReturnData.getTradeInfo());

    } catch (UnsupportedEncodingException e) {
      return ResultGenerator.genFailResult(ResultCode.RESPONSE_JSON_ERROR.getCode(),
          ResultCode.RESPONSE_JSON_ERROR.getMessage());
    }
    return ResultGenerator.genSuccessResult(pageInfo);
  }


}
