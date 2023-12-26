package com.wasupstudio.controller;

import com.wasupstudio.constant.ProjectConstant;
import com.wasupstudio.exception.ResultGenerator;
import com.wasupstudio.model.BasePageInfo;
import com.wasupstudio.model.CashFlowData;
import com.wasupstudio.model.Result;
import com.wasupstudio.model.dto.CashFlowReturnDataDTO;
import com.wasupstudio.model.dto.OrderDTO;
import com.wasupstudio.model.dto.OrderSearchDTO;
import com.wasupstudio.model.dto.ProductDTO;
import com.wasupstudio.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


@Api(tags = "金流相關 API")
@RestController
@RequestMapping("/api/cash")
@Slf4j
public class CashFlowController {

    @Autowired
    OrderService orderService;
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    MemberService memberService;
    @Autowired
    ProductService productService;
    @Autowired
    LicenseService licenseService;

    @ApiOperation("取得產品資訊")
    @GetMapping(value = "/products")
    @Transactional
    protected Result getProduct() {
        BasePageInfo allData = productService.findAllData();
        return ResultGenerator.genSuccessResult(allData);
    }

    @ApiOperation("新增產品資訊")
    @PostMapping(value = "/products")
    @Transactional
    protected Result insertProduct(@RequestBody ProductDTO productDTO) {
        productService.save(productDTO);
        return ResultGenerator.genSuccessResult();
    }
    @ApiOperation("刪除產品資訊")
    @DeleteMapping(value = "/products/{productId}")
    @Transactional
    protected Result deleteProduct(@PathVariable Long productId) {
        productService.delete(productId);
        return ResultGenerator.genSuccessResult();
    }
    @ApiOperation("更新產品資訊")
    @PutMapping(value = "/products/{productId}")
    @Transactional
    protected Result updateProduct(@PathVariable Integer productId, @RequestBody ProductDTO productDTO) {
        productDTO.setProductId(Long.valueOf(productId));
        productService.update(productDTO);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation("取得訂單資料")
    @PostMapping(value = "/orders")
    @Transactional
    protected Result getOrderList(@RequestBody OrderSearchDTO orderSearchDTO) throws Exception {
        BasePageInfo allData = orderService.findByCondiction(orderSearchDTO);
        return ResultGenerator.genSuccessResult(allData);
    }

    @ApiOperation("取得藍新加密資料(一般)/建立訂單")
    @PostMapping(value = "/order")
    @Transactional
    protected Result createOrderList(@RequestBody OrderDTO orderDTO) throws Exception {
        log.info("orderDTO = " + orderDTO);
        CashFlowData cashFlowData = orderService.creatOrder(orderDTO);
        // 前端頁面會送來未加密過的資訊，利用這個API把資訊加密之後送到三方
        return ResultGenerator.genSuccessResult(cashFlowData);
    }

    @ApiOperation("藍新callback方法")
    @PostMapping(value = "/callback")
    protected Result getReturnData(@ModelAttribute CashFlowReturnDataDTO cashFlowReturnDataDTO) {
        log.info("callback init result:{}", cashFlowReturnDataDTO);

        if (orderService.orderCallBack(cashFlowReturnDataDTO)) {
            return ResultGenerator.genSuccessResult(ProjectConstant.TradeType.SUCCESS);
        }
        return ResultGenerator.genFailResult(ProjectConstant.TradeType.FAIL);
    }

}
