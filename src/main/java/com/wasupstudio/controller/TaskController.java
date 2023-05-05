package com.wasupstudio.controller;

import com.wasupstudio.enums.ResultCode;
import com.wasupstudio.exception.ResultGenerator;
import com.wasupstudio.model.BasePageInfo;
import com.wasupstudio.model.Result;
import com.wasupstudio.model.dto.ScriptDTO;
import com.wasupstudio.model.dto.TaskDTO;
import com.wasupstudio.model.entity.ScriptEntity;
import com.wasupstudio.model.entity.TaskEntity;
import com.wasupstudio.service.ScriptService;
import com.wasupstudio.service.TaskService;
import com.wasupstudio.util.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

@Api(tags = "Task API", value = "Task 相關 API")
@RestController
@RequestMapping("/api/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @ApiOperation(value = "取得所有 Task 資料", notes = "取得所有 Task 資料，並回傳一個 BasePageInfo 物件")
    @GetMapping
    public Result getAllData() {
        BasePageInfo pageInfo = taskService.findAllData();
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @ApiOperation(value = "取得單一 Task 資料", notes = "根據 Task ID 取得單一 Task 資料")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "回傳該 Task 資料"),
            @ApiResponse(code = 404, message = "該 Task 資料不存在")
    })
    @GetMapping("/{id}")
    public Result getOneData(@PathVariable Integer id) {
        TaskEntity taskEntity = taskService.findOne(id);
        if (taskEntity == null){
            return ResultGenerator.genSuccessResult(ResultCode.DATA_NOT_EXIST.getMessage());
        }
        return ResultGenerator.genSuccessResult(taskEntity);
    }

    @ApiOperation(value = "新增一筆 Task 資料", notes = "新增一筆 Task 資料，並回傳 Result 結果")
    @PostMapping
    public Result save(@RequestBody @Valid TaskDTO taskDTO, BindingResult bindingResult) {
        String account = JwtUtils.getMemberAccount();
        taskDTO.setAuthor(account);
        if (bindingResult.hasErrors()) {
            String errorMsg = bindingResult.getFieldErrors().stream()
                    .map(error -> error.getField() + " " + error.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            return ResultGenerator.genFailResult(errorMsg);
        }
        taskService.save(taskDTO);
        return ResultGenerator.genSuccessResult(ResultCode.ADD_SUCCESS.getMessage());
    }

    @ApiOperation(value = "更新一筆 Task 資料", notes = "根據 Task ID 更新一筆 Task 資料，並回傳 Result 結果")
    @PutMapping("/{id}")
    public Result update(@PathVariable Integer id, @RequestBody @Valid TaskDTO taskDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMsg = bindingResult.getFieldErrors().stream()
                    .map(error -> error.getField() + " " + error.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            return ResultGenerator.genFailResult(errorMsg);
        }
        String account = JwtUtils.getMemberAccount();
        taskDTO.setAuthor(account);
        taskDTO.setTaskId(id);
        taskService.update(taskDTO);
        return ResultGenerator.genSuccessResult(ResultCode.SAVE_SUCCESS.getMessage());
    }
}
