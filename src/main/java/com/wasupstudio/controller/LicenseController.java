package com.wasupstudio.controller;

import com.wasupstudio.constant.ProjectConstant;
import com.wasupstudio.enums.LicenseEnum;
import com.wasupstudio.enums.ResultCode;
import com.wasupstudio.exception.ResultGenerator;
import com.wasupstudio.model.BasePageInfo;
import com.wasupstudio.model.Result;
import com.wasupstudio.model.dto.LicenseDTO;
import com.wasupstudio.model.entity.LicenseEntity;
import com.wasupstudio.model.entity.MemberEntity;
import com.wasupstudio.service.LicenseService;
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
import java.util.UUID;
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
     * @return Result 結果
     */
    @PostMapping
    @ApiOperation(value = "新增啟動碼數據")
    public Result save(@RequestParam Integer count){
        MemberEntity member = JwtUtils.getMember();
        if(member.getRole().equals(MemberEntity.Role.ROLE_ADMIN)){
            member.setName("Admin - " + member.getName());
        }
        for (int startUpCount = 0 ; startUpCount < count ; startUpCount++){
            LicenseDTO licenseDTO = LicenseDTO.builder()
                    .startTime(new Date())
                    .generate(member.getName())
                    .build();
            licenseService.save(licenseDTO);
        }
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

    /**
     * 啟動碼驗證
     * @return Result 結果
     */
    @PostMapping("/verify")
    @ApiOperation(value = "啟動碼驗證")
    public Result verifyLicense(@RequestBody LicenseDTO licenseDTO){

        MemberEntity member = JwtUtils.getMember();

        licenseDTO.setLicenseKey(licenseDTO.getLicenseKey());
        licenseDTO.setCustomerEmail(member.getEmail());
        licenseDTO.setCustomerName(member.getName());

        List<LicenseEntity> licenseEntityList = licenseService.findByEmailAndActivated(member.getEmail());

        List<LicenseEntity> unused = licenseService.findByLicenseKeyAndActivated(licenseDTO.getLicenseKey(), 1);

        if (licenseEntityList.size() > 0){
            return ResultGenerator.genFailResult(ResultCode.LICENSE_OF_REDEMPTION_TOO_MANY_TIMES.getMessage());
        }
        if (unused.size() > 0){
            return ResultGenerator.genFailResult(ResultCode.LICENSE_OF_REDEMPTION_IS_USED.getMessage());
        }

        boolean verify = licenseService.verify(licenseDTO);
        if (verify){
            return ResultGenerator.genSuccessResult(ResultCode.LICENSE_ACTIVATED_SUCCESS.getMessage());
        }

        return ResultGenerator.genFailResult(ResultCode.LICENSE_ACTIVATED_FAIL.getMessage());
    }
}
