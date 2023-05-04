package com.wasupstudio.controller;

import com.google.gson.Gson;
import com.wasupstudio.enums.ResultCode;
import com.wasupstudio.exception.ResultGenerator;
import com.wasupstudio.model.BasePageInfo;
import com.wasupstudio.model.Result;
import com.wasupstudio.model.dto.LicenseDTO;
import com.wasupstudio.model.dto.ScriptDTO;
import com.wasupstudio.model.entity.LicenseEntity;
import com.wasupstudio.model.entity.ScriptEntity;
import com.wasupstudio.service.LicenseService;
import com.wasupstudio.service.ScriptService;
import com.wasupstudio.util.DateUtils;
import com.wasupstudio.util.JwtUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Date;
import java.util.stream.Collectors;

@Api
@RestController
@RequestMapping("/api/script")
public class ScriptController {

    @Autowired
    private ScriptService scriptService;

    @GetMapping
    public Result getAllData() {
        BasePageInfo pageInfo = scriptService.findAllData();
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @GetMapping("/{id}")
    public Result getOneData(@PathVariable Integer id) {
        ScriptEntity scriptEntity = scriptService.findOne(id);
        if (scriptEntity == null){
            return ResultGenerator.genSuccessResult(ResultCode.DATA_NOT_EXIST.getMessage());
        }
        return ResultGenerator.genSuccessResult(scriptEntity);
    }

    @PostMapping
    public Result save(@RequestBody @Valid ScriptDTO scriptDTO, BindingResult bindingResult) {
        String account = JwtUtils.getMemberAccount();
        scriptDTO.setAuthor(account);
        if (bindingResult.hasErrors()) {
            String errorMsg = bindingResult.getFieldErrors().stream()
                    .map(error -> error.getField() + " " + error.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            return ResultGenerator.genFailResult(errorMsg);
        }
        scriptService.save(scriptDTO);
        return ResultGenerator.genSuccessResult(ResultCode.ADD_SUCCESS.getMessage());
    }

    @PutMapping("/{id}")
    public Result update(@PathVariable Integer id, @RequestBody @Valid ScriptDTO scriptDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMsg = bindingResult.getFieldErrors().stream()
                    .map(error -> error.getField() + " " + error.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            return ResultGenerator.genFailResult(errorMsg);
        }

        scriptDTO.setScriptId(id);
        scriptService.update(scriptDTO);
        return ResultGenerator.genSuccessResult(ResultCode.SAVE_SUCCESS.getMessage());
    }
}
