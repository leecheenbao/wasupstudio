package com.wasupstudio.controller;

import com.wasupstudio.model.Result;
import com.wasupstudio.enums.ResultCode;
import com.wasupstudio.exception.ResultGenerator;
import com.wasupstudio.model.dto.MemberDTO;
import com.wasupstudio.model.entity.MemberEntity;
import com.wasupstudio.service.MemberService;
import com.wasupstudio.model.BasePageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

@Api(tags = "用戶相關 Member API")
@RestController
@RequestMapping("/api/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @ApiOperation(value = "獲取所有會員資料", notes = "獲取所有會員資料")
    @GetMapping
    public Result getAllData() {
        BasePageInfo pageInfo = memberService.findAllData();
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @ApiOperation(value = "獲取指定ID的會員資料", notes = "獲取指定ID的會員資料")
    @GetMapping("/{id}")
    public Result getOneData(@PathVariable Integer id) {
        MemberEntity licenseEntity = memberService.findOne(id);
        return ResultGenerator.genSuccessResult(licenseEntity);
    }


}
