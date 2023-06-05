package com.wasupstudio.controller;

import com.google.gson.Gson;
import com.wasupstudio.constant.ProjectConstant;
import com.wasupstudio.model.Result;
import com.wasupstudio.enums.ResultCode;
import com.wasupstudio.exception.ResultGenerator;
import com.wasupstudio.model.dto.LicenseDTO;
import com.wasupstudio.model.entity.LicenseEntity;
import com.wasupstudio.model.entity.MemberEntity;
import com.wasupstudio.service.LicenseService;
import com.wasupstudio.model.BasePageInfo;
import com.wasupstudio.service.MemberService;
import com.wasupstudio.util.DateUtils;
import com.wasupstudio.util.JwtUtils;
import com.wasupstudio.util.MailUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * License Controller API
 */
@Api(tags = "啟動碼相關 License Controller API")
@RestController
@RequestMapping("/api/license")
public class LicenseController {

    @Autowired
    private LicenseService licenseService;
    @Autowired
    private MemberService memberService;

    /**
     * 查詢所有啟動碼數據
     *
     * @return Result 結果
     */
    @GetMapping
    @ApiOperation(value = "查詢所有啟動碼數據")
    public Result getAllData() {
        BasePageInfo pageInfo = licenseService.findAllData();
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    /**
     * 根據id查詢啟動碼數據
     *
     * @param id id
     * @return Result 結果
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "根據id查詢啟動碼數據")
    public Result getOneData(@PathVariable Integer id) {
        LicenseEntity licenseEntity = licenseService.findOne(id);
        if (licenseEntity == null){
            return ResultGenerator.genSuccessResult(ResultCode.DATA_NOT_EXIST.getMessage());
        }
        return ResultGenerator.genSuccessResult(licenseEntity);
    }

    /**
     * 根據激活時間查詢啟動碼數據
     *
     * @param licenseDTO License DTO
     * @return Result 結果
     */
    @PostMapping("/getLicenseByActTime")
    @ApiOperation(value = "根據激活時間查詢啟動碼數據")
    public Result getDataByTime(@RequestBody @Valid LicenseDTO licenseDTO) {
        Date startTime = DateUtils.getEndDate(licenseDTO.getStartTime());
        Date endTime = DateUtils.getEndDate(licenseDTO.getEndTime());

        DateUtils.getDaysBetween(startTime,endTime);

        BasePageInfo pageInfo = licenseService.findByACTDate(startTime, endTime);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    /**
     * 新增啟動碼數據
     *
     * @param licenseDTO      License DTO
     * @param bindingResult   綁定結果
     * @return Result 結果
     */
    @PostMapping
    @ApiOperation(value = "新增啟動碼數據")
    public Result save(@RequestBody @Valid LicenseDTO licenseDTO, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            String errorMsg = bindingResult.getFieldErrors().stream()
                    .map(error -> error.getField() + " " + error.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            return ResultGenerator.genFailResult(errorMsg);
        }

        List<LicenseEntity> list = licenseService.findByEmailAndActivated(licenseDTO);
        if (!list.isEmpty()) {
            return ResultGenerator.genFailResult(ResultCode.LICENSE_OF_REDEMPTION_TOO_MANY_TIMES.getMessage());
        }

        MemberEntity memberEntity = memberService.getAdminByEmail(licenseDTO.getCustomerEmail());
        if (memberEntity == null){
            return ResultGenerator.genFailResult(ResultCode.LICENSE_OF_ACCOUNT_NOT_FOUND.getMessage());
        }

        String memberId = String.valueOf(memberEntity.getId());
        MailUtil.sendMail(ProjectConstant.MailType.START_KEY, "" ,licenseDTO.getCustomerEmail());
        licenseService.save(licenseDTO);

        return ResultGenerator.genSuccessResult(ResultCode.ADD_SUCCESS.getMessage());
    }

    /**
     * 更新啟動碼數據
     *
     * @param id              id
     * @param licenseDTO      License DTO
     * @param bindingResult   綁定結果
     * @return Result 結果
     */
    @PutMapping("/{id}")
    @ApiOperation(value = "更新啟動碼數據")
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
