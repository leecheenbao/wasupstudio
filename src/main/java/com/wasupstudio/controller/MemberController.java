package com.wasupstudio.controller;

import com.wasupstudio.model.Result;
import com.wasupstudio.enums.ResultCode;
import com.wasupstudio.exception.ResultGenerator;
import com.wasupstudio.model.dto.MemberDTO;
import com.wasupstudio.model.entity.MemberEntity;
import com.wasupstudio.service.MemberService;
import com.wasupstudio.model.BasePageInfo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

@Api
@RestController
@RequestMapping("/api/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping
    public Result getAllData() {
        BasePageInfo pageInfo = memberService.findAllData();
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @GetMapping("/{id}")
    public Result getOneData(@PathVariable Integer id) {
        MemberEntity licenseEntity = memberService.findOne(id);
        return ResultGenerator.genSuccessResult(licenseEntity);
    }

    @PutMapping("/{id}")
    public Result update(@PathVariable Integer id, @RequestBody @Valid MemberDTO memberDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMsg = bindingResult.getFieldErrors().stream()
                    .map(error -> error.getField() + " " + error.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            return ResultGenerator.genFailResult(errorMsg);
        }

        memberDTO.setId(id);
        memberService.update(memberDTO);
        return ResultGenerator.genSuccessResult(ResultCode.SAVE_SUCCESS.getMessage());
    }
}
