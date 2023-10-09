package com.wasupstudio.controller;

import com.wasupstudio.exception.ResultGenerator;
import com.wasupstudio.model.Result;
import com.wasupstudio.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(tags = "統計 API")
@RestController
@RequestMapping("/api/report")
public class ReportController {

    @Autowired
    private MemberService memberService;
    /*
    * 近期登入人數(login)
    * 用戶年齡分佈(age)
    * 各劇本使用近況(script)
    * 隸屬機構統計(category)
    * 各劇本結局統計(script-ending)
    */
    @ApiOperation(value = "近期登入人數(login)")
    @GetMapping("/login")
    public Result getLogin() {
        return ResultGenerator.genSuccessResult(memberService.findLoginFor7Day());
    }
    @ApiOperation(value = "用戶年齡分佈(age)")
    @GetMapping("/age")
    public Result getAge() {
        return ResultGenerator.genSuccessResult(memberService.findAgeDistributions());
    }

    @ApiOperation(value = "隸屬機構統計(category)")
    @GetMapping("/category")
    public Result getOrganization() {
        return ResultGenerator.genSuccessResult(memberService.findCategory());
    }

    @ApiOperation(value = "各劇本使用近況(script)")
    @GetMapping("/script")
    public Result getScript() {
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "各劇本結局統計(script-ending)")
    @GetMapping("/script-ending")
    public Result getScriptEnding() {
        return ResultGenerator.genSuccessResult();
    }

}
