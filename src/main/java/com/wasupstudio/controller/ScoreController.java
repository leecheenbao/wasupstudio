package com.wasupstudio.controller;

import com.wasupstudio.enums.ResultCode;
import com.wasupstudio.exception.ResultGenerator;
import com.wasupstudio.model.BasePageInfo;
import com.wasupstudio.model.Result;
import com.wasupstudio.model.dto.ScriptQuestionDTO;
import com.wasupstudio.model.entity.ScriptQuestionEntity;
import com.wasupstudio.service.ScriptQuestionOptionService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Api(tags = "評分 Score API")
@RestController
@RequestMapping("/api/score")
public class ScoreController {

    @Autowired
    ScriptQuestionOptionService scriptQuestionOptionService;

    @ApiOperation(value = "批量新增任務資料", notes = "批量新增學習單資料，並回傳 Result 結果")
    @PostMapping
    public Result save(@RequestBody @Valid List<ScriptQuestionDTO> scriptQuestionDTOs, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            String errorMsg = bindingResult.getFieldErrors().stream()
                    .map(error -> error.getField() + " " + error.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            return ResultGenerator.genFailResult(errorMsg);
        }
        for (ScriptQuestionDTO scriptQuestionDTO : scriptQuestionDTOs){
            scriptQuestionOptionService.save(scriptQuestionDTO);
        }
        return ResultGenerator.genSuccessResult(ResultCode.ADD_SUCCESS.getMessage());
    }

    @ApiOperation(value = "刪除任務資料", notes = "刪除學習單資料")
    @ApiImplicitParam(name = "questionId", value = "questionId", required = true, dataType = "int")
    @DeleteMapping("/{questionId}")
    public Result delete(@PathVariable Integer questionId) {
        scriptQuestionOptionService.delete(questionId);
        return ResultGenerator.genSuccessResult(ResultCode.DELETE_SUCCESS.getMessage());
    }
    @ApiOperation(value = "取得所有分數資料")
    @GetMapping
    public Result getAllData() {
        BasePageInfo pageInfo = scriptQuestionOptionService.findAllData();
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @ApiOperation(value = "取得單一任務學習單分數", notes = "根據 ID 取得單一 任務學習單資料")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "回傳該 任務資料"),
            @ApiResponse(code = 404, message = "該 任務資料不存在")
    })
    @GetMapping("/{taskId}")
    public Result getOneData(@PathVariable Integer taskId) {
        BasePageInfo<ScriptQuestionEntity> scriptQuestionEntity = scriptQuestionOptionService.findByTaskId(taskId);
        if (scriptQuestionEntity == null){
            return ResultGenerator.genSuccessResult(ResultCode.DATA_NOT_EXIST.getMessage());
        }
        return ResultGenerator.genSuccessResult(scriptQuestionEntity);
    }
}
