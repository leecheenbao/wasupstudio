package com.wasupstudio.controller;

import com.wasupstudio.exception.ResultGenerator;
import com.wasupstudio.model.BasePageInfo;
import com.wasupstudio.model.CashFlowData;
import com.wasupstudio.model.CashFlowReturnData;
import com.wasupstudio.model.Result;
import com.wasupstudio.model.dto.OrderDTO;
import com.wasupstudio.service.OrderService;
import com.wasupstudio.util.CashFlowUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import lombok.val;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.expression.ParseException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


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

  //  @Autowired
//  OrderService orderService;

  @Autowired
  RestTemplate restTemplate;

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }

  @ApiOperation("取得藍新加密資料(一般)/建立訂單")
  @PostMapping(value = "/order")
  protected CashFlowData createOrderList() throws Exception {
    CashFlowData cashFlowData = new CashFlowData();
    cashFlowData.setMerchantID_(merchantID);
    cashFlowData.setVersion("2.0");
    CashFlowUtils cashFlowUtils = new CashFlowUtils();
    // 前端頁面會送來未加密過的資訊，利用這個API把資訊加密之後送到三方
    Map<String, Object> params = new HashMap<>();
    params.put("MerchantID", merchantID);
    params.put("TimeStamp", String.valueOf(System.currentTimeMillis() / 1000));
    params.put("Version", "2.0");
    params.put("RespondType", "JSON");
    params.put("MerchantOrderNo", "test0315001");
    params.put("Amt", "30");
    params.put("ItemDesc", "test");
    params.put("VACC", "1");
    params.put("WEBATM", "1");
    params.put("UNIONPAY", "0");
    params.put("CREDIT", "0");
    params.put("NotifyURL", "https://webhook.site/91346899-a36b-4fc6-915b-7b397a63e213");
    params.put("ReturnURL", "");
    params.put("LoginType", "0");
    params.put("InstFlag", "0");

    String dataInfo = cashFlowUtils.getDataInfo(params);
    String tradeInfo = cashFlowUtils.encrypt(dataInfo, hashKey, hashIV);
    String tradeSha = cashFlowUtils.getTradeSha(tradeInfo, hashKey, hashIV);
    cashFlowData.setTradeInfo(tradeInfo);
    cashFlowData.setTradeSha(tradeSha);

    return cashFlowData;
  }

  @ApiOperation("藍新callback方法")
  @PostMapping(value = "/callback")
  protected Result getReturnData(CashFlowReturnData cashFlowReturnData) {
    BasePageInfo pageInfo = new BasePageInfo<>();
    //接收三方收到的訊息然後解密處理實作callback方法
    return ResultGenerator.genSuccessResult(pageInfo);
  }


}
