package com.wasupstudio.controller;

import com.google.gson.Gson;
import com.wasupstudio.exception.Result;
import com.wasupstudio.exception.ResultCode;
import com.wasupstudio.exception.ResultGenerator;
import com.wasupstudio.model.dto.LicenseDTO;
import com.wasupstudio.model.entity.LicenseEntity;
import com.wasupstudio.service.LicenseService;
import com.wasupstudio.model.BasePageInfo;
import com.wasupstudio.util.DateUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.stream.Collectors;

@Api
@RestController
@RequestMapping("/api/license")
public class LicenseController {

    @Autowired
    private LicenseService licenseService;

    @GetMapping
    public Result getAllData() {
        BasePageInfo pageInfo = licenseService.findAllData();
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @GetMapping("/{id}")
    public Result getOneData(@PathVariable Integer id) {
        LicenseEntity licenseEntity = licenseService.findOne(id);
        if (licenseEntity == null){
            return ResultGenerator.genSuccessResult(ResultCode.DATA_NOT_EXIST.getMessage());
        }
        return ResultGenerator.genSuccessResult(licenseEntity);
    }

    @PostMapping("/getLicenseByActTime")
    public Result getDataByTime(@RequestBody @Valid LicenseDTO licenseDTO) {
        String startTime = licenseDTO.getStartTime() + " 00:00:00";
        String endTime = licenseDTO.getEndTime() + " 23:59:59";
        Date start = DateUtils.parse(startTime, DateUtils.YYYY_MM_DD_HH_MM_SS);
        Date end = DateUtils.parse(endTime, DateUtils.YYYY_MM_DD_HH_MM_SS);
        DateUtils.getDaysBetween(start,end);

        BasePageInfo pageInfo = licenseService.findByACTDate(start, end);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @PostMapping
    public Result save(@RequestBody @Valid LicenseDTO licenseDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMsg = bindingResult.getFieldErrors().stream()
                    .map(error -> error.getField() + " " + error.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            return ResultGenerator.genFailResult(errorMsg);
        }
        licenseService.save(licenseDTO);
        Gson gson = new Gson();
        return ResultGenerator.genSuccessResult(ResultCode.ADD_SUCCESS.getMessage());
    }

    @PutMapping("/{id}")
    public Result update(@PathVariable Integer id, @RequestBody @Valid LicenseDTO licenseDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMsg = bindingResult.getFieldErrors().stream()
                    .map(error -> error.getField() + " " + error.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            return ResultGenerator.genFailResult(errorMsg);
        }

        licenseDTO.setId(id);
        licenseService.update(licenseDTO);
        return ResultGenerator.genSuccessResult(ResultCode.SAVE_SUCCESS.getMessage());
    }
}
