package com.wasupstudio.controller;

import com.wasupstudio.converter.ScriptConverter;
import com.wasupstudio.enums.ResultCode;
import com.wasupstudio.exception.ResultGenerator;
import com.wasupstudio.model.BasePageInfo;
import com.wasupstudio.model.Result;
import com.wasupstudio.model.dto.MediaDTO;
import com.wasupstudio.model.dto.ScriptDTO;
import com.wasupstudio.model.entity.MemberEntity;
import com.wasupstudio.model.entity.ScriptEntity;
import com.wasupstudio.service.FileService;
import com.wasupstudio.service.MediaService;
import com.wasupstudio.service.ScriptService;
import com.wasupstudio.util.JwtUtils;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.stream.Collectors;

@Api(tags = "劇本相關 Script API")
@RestController
@RequestMapping("/api/script")
public class ScriptController {

    @Autowired
    private ScriptService scriptService;

    @Autowired
    private MediaService mediaService;

    @Autowired
    private ScriptConverter scriptConverter;

    @Autowired
    private FileService fileService;

    @ApiOperation(value = "取得劇本資料")
    @GetMapping
    public Result getAllData() {
        BasePageInfo pageInfo = scriptService.findAllData();
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @ApiOperation(value = "取得單一劇本資料")
    @ApiImplicitParam(name = "id", value = "Script ID", required = true, dataType = "int", paramType = "path")
    @GetMapping("/{id}")
    public Result getOneData(@PathVariable Integer id) {
        ScriptEntity scriptEntity = scriptService.findOne(id);
        if (scriptEntity == null){
            return ResultGenerator.genSuccessResult(ResultCode.DATA_NOT_EXIST.getMessage());
        }
        ScriptDTO scriptDTO = scriptConverter.map(scriptEntity);
        scriptDTO.setMediaDTO(mediaService.findByScriptId(id));

        return ResultGenerator.genSuccessResult(scriptDTO);
    }

    @ApiOperation(value = "新增一筆劇本資料")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request")
    })
    @PostMapping
    public Result save(@RequestBody ScriptDTO scriptDTO, BindingResult bindingResult) {
        MemberEntity member = JwtUtils.getMember();
        scriptDTO.setAuthor(member.getEmail());
        if (bindingResult.hasErrors()) {
            String errorMsg = bindingResult.getFieldErrors().stream()
                    .map(error -> error.getField() + " " + error.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            return ResultGenerator.genFailResult(errorMsg);
        }


        scriptService.save(scriptDTO);
        return ResultGenerator.genSuccessResult(ResultCode.ADD_SUCCESS.getMessage());
    }

    @ApiOperation(value = "更新一筆劇本資料")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "Script ID", required = true, dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "scriptDTO", value = "Script DTO", required = true, dataType = "ScriptDTO", paramType = "body")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request")
    })
    @PutMapping("/{id}")
    public Result update(@PathVariable Integer id, @RequestBody ScriptDTO scriptDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMsg = bindingResult.getFieldErrors().stream()
                    .map(error -> error.getField() + " " + error.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            return ResultGenerator.genFailResult(errorMsg);
        }
        MemberEntity member = JwtUtils.getMember();
        scriptDTO.setAuthor(member.getEmail());
        scriptDTO.setScriptId(id);
        scriptService.update(scriptDTO);
        return ResultGenerator.genSuccessResult(ResultCode.SAVE_SUCCESS.getMessage());
    }

    @PostMapping("/upload/{id}")
    public Result file(@PathVariable Integer id, @RequestBody MediaDTO mediaDTO, @RequestParam(value = "file") MultipartFile file) {
        mediaDTO.setScriptId(id);
        // 处理文件上传
        if (!file.isEmpty()) {
            try {
                // 获取文件字节数组
                byte[] fileBytes = file.getBytes();

                // 进行文件处理操作，例如保存到服务器或者进行其他操作

                // 更新脚本等其他逻辑...

                return ResultGenerator.genSuccessResult(ResultCode.SAVE_SUCCESS.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // 如果没有上传文件或发生异常，返回错误结果
        return ResultGenerator.genFailResult(ResultCode.UPLOAD_ERROR.getMessage());
    }

    @ApiOperation(value = "上傳文件", notes = "上傳文件接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "文件", required = true, dataType = "MultipartFile"),
    })
    @PostMapping("/upload")
    @ResponseBody
    public Result handleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {

        return ResultGenerator.genSuccessResult(ResultCode.UPLOAD_SUCCESS.getMessage());
    }

    @ApiOperation(value = "上傳影片", notes = "上傳影片接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "文件", required = true, dataType = "MultipartFile"),
    })
    @PostMapping("/upload/video")
    @ResponseBody
    public Result uploadVideo(@RequestParam("file") MultipartFile file) {
        return ResultGenerator.genSuccessResult(ResultCode.UPLOAD_SUCCESS.getMessage());
    }
}
