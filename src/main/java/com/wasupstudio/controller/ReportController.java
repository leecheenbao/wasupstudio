package com.wasupstudio.controller;

import com.wasupstudio.exception.ResultGenerator;
import com.wasupstudio.model.BasePageInfo;
import com.wasupstudio.model.Result;
import com.wasupstudio.model.dto.LoginRecordQueryDTO;
import com.wasupstudio.service.LoginRecordsService;
import com.wasupstudio.service.MemberService;
import com.wasupstudio.service.ScriptQuestionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(tags = "統計 API")
@RestController
@RequestMapping("/api/report")
public class ReportController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private LoginRecordsService loginRecordsService;

    @Autowired
    private ScriptQuestionService scriptQuestionService;

    /*
    * 近期登入人數(login) V
    * 用戶年齡分佈(age) V
    * 隸屬機構統計(category) V
    * 各劇本結局統計(script-ending)
    * 各劇本使用近況(script)
    * 各劇本答題狀況
    */
    @ApiOperation(value = "近期登入人數(login)")
    @GetMapping("/login")
    public Result getLogin() {
        return ResultGenerator.genSuccessResult(loginRecordsService.query7DayLoginRecord());
    }

    @ApiOperation(value = "近期登入人數(login) - 依照時間搜尋")
    @GetMapping("/login/day")
    public Result getLoginByDay(@RequestBody LoginRecordQueryDTO dto) {
        return ResultGenerator.genSuccessResult(loginRecordsService.queryLoginRecord(dto.getStartDate(), dto.getEndDate()));
    }

    @ApiOperation(value = "用戶年齡分佈(age)")
    @GetMapping("/age")
    public Result getAge() {
        return ResultGenerator.genSuccessResult(memberService.findAgeDistributions());
    }

    @ApiOperation(value = "隸屬機構統計(category)")
    @GetMapping("/category")
    public Result getCategory() {
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
        BasePageInfo basePageInfo = scriptQuestionService.findReportForEnding();
        return ResultGenerator.genSuccessResult(basePageInfo);
    }

    @ApiOperation(value = "各劇本結局統計(script-ending)")
    @GetMapping("/script-distribution")
    public Result getScriptDistribution() {
        BasePageInfo basePageInfo = scriptQuestionService.scoreDistribution();
        return ResultGenerator.genSuccessResult(basePageInfo);
    }
}
