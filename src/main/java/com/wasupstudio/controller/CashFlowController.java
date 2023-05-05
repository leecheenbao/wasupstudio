package com.wasupstudio.controller;

import com.wasupstudio.exception.ResultGenerator;
import com.wasupstudio.model.BasePageInfo;
import com.wasupstudio.model.CashFlowData;
import com.wasupstudio.model.CashFlowReturnData;
import com.wasupstudio.model.Result;
import com.wasupstudio.util.CashFlowUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.expression.ParseException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@Api(tags = "金流相關 API")
@RestController
@RequestMapping("/api/cash")
public class CashFlowController {

    /* 正式 */
    @Value("${newebpay.hashKey}")
    private  String hashKey;
    @Value("${newebpay.hashIV}")
    private  String hashIV;
    @Value("${newebpay.merchantID}")
    private  String merchantID;

    @ApiOperation("取得藍新加密資料(一般)/建立訂單")
    @PostMapping(value = "/order")
    protected CashFlowData createOrderList() throws IOException, ParseException {
        CashFlowData cashFlowData = new CashFlowData();
        CashFlowUtils cashFlowUtils = new CashFlowUtils();

        return cashFlowData;
    }

    @ApiOperation("藍新callback方法")
    @PostMapping(value = "/callback")
    protected Result getReturnData(CashFlowReturnData cashFlowReturnData) {
        BasePageInfo pageInfo = new BasePageInfo<>();
        //實作callback方法
        return ResultGenerator.genSuccessResult(pageInfo);
    }



}
