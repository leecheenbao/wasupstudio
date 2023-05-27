package com.wasupstudio.controller;

import com.wasupstudio.converter.ScriptConverter;
import com.wasupstudio.enums.FileTypeEnum;
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
import com.wasupstudio.util.DateUtils;
import com.wasupstudio.util.FileUtils;
import com.wasupstudio.util.JwtUtils;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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

    @ApiOperation(value = "上傳文件", notes = "上傳文件接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "文件", required = true, dataType = "MultipartFile"),
    })
    @PostMapping("/upload/{scriptId}")
    @ResponseBody
    @Transactional
    public Result handleFileUpload(@PathVariable Integer scriptId, @RequestParam("file") MultipartFile file) throws IOException {
        if (FileUtils.validateFileExtension(file.getOriginalFilename())){
            return ResultGenerator.genSuccessResult((ResultCode.UPLOAD_FORMAT_ERROR.getMessage()));
        }
        if (FileUtils.validateFileSize(file)){
            String type = FileTypeEnum.getEnum(FileUtils.checkFileType(file.getOriginalFilename())).getDesc();
            String size = String.valueOf(FileUtils.MAX_FILE_SIZE);
            return ResultGenerator.genSuccessResult(ResultCode.UPLOAD_MAX_ERROR.getFormattedMessage(type,size));
        }

        String fileName = DateUtils.currentTimeMillis() + "." +FileUtils.getFileExtension(file.getOriginalFilename());
        String mediaType = FileUtils.checkFileType(fileName);

        String filePath = fileService.uploadFile(file.getBytes(), fileName, mediaType);
        MediaDTO mediaDTO = new MediaDTO();
        mediaDTO.setScriptId(scriptId);
        mediaDTO.setFilePath(filePath);
        mediaDTO.setMediaType(mediaType);
        mediaService.save(mediaDTO);
        return ResultGenerator.genSuccessResult(ResultCode.UPLOAD_SUCCESS.getMessage());
    }

    @ApiOperation(value = "刪除檔案", notes = "刪除檔案接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "scriptId", value = "劇本ID", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "mediaId", value = "影音ID", required = true, dataType = "Integer")
    })
    @DeleteMapping("/media/{scriptId}/{mediaId}")
    @ResponseBody
    @Transactional
    public Result handleFileDelete(@PathVariable Integer scriptId, @PathVariable Integer mediaId) throws IOException {

        MediaDTO mediaDTO = mediaService.findByScriptIdAndMediaId(scriptId, mediaId);
        if (mediaDTO == null) {
            return ResultGenerator.genSuccessResult(ResultCode.MATERIAL_INFO_NOT_EXIST.getMessage());
        }
        // 取得最後一個字節獲取storage的object_name
        String[] str = mediaDTO.getFilePath().split(File.separator);
        String lastByte = str[str.length - 1];

        fileService.removeFile(lastByte);
        mediaService.delete(scriptId, mediaId);
        return ResultGenerator.genSuccessResult(ResultCode.DELETE_SUCCESS.getMessage());
    }
}
