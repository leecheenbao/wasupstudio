package com.wasupstudio.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wasupstudio.enums.FileTypeEnum;
import com.wasupstudio.enums.ResultCode;
import com.wasupstudio.exception.ResultGenerator;
import com.wasupstudio.model.BasePageInfo;
import com.wasupstudio.model.Result;
import com.wasupstudio.model.dto.MediaDTO;
import com.wasupstudio.model.dto.ParentConfiglDTO;
import com.wasupstudio.model.dto.ScriptDTO;
import com.wasupstudio.model.dto.ScriptDetailDTO;
import com.wasupstudio.model.entity.MemberEntity;
import com.wasupstudio.model.entity.ParentConfiglEntity;
import com.wasupstudio.model.entity.ScriptDetailEntity;
import com.wasupstudio.model.entity.ScriptEntity;
import com.wasupstudio.model.query.ScriptQuery;
import com.wasupstudio.service.*;
import com.wasupstudio.util.DateUtils;
import com.wasupstudio.util.FileUtils;
import com.wasupstudio.util.JwtUtils;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Api(tags = "劇本相關 Script API")
@RestController
@RequestMapping("/api/script")
public class ScriptController {

    @Autowired
    private ScriptService scriptService;
    @Autowired
    private ScriptDetailService scriptDetailService;

    @Autowired
    private StudentConfigService studentConfigService;
    @Autowired
    private ParentConfigService parentConfigService;
    @Autowired
    private MediaService mediaService;
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
        scriptEntity.getPreamble();
        if (scriptEntity == null){
            return ResultGenerator.genSuccessResult(ResultCode.DATA_NOT_EXIST.getMessage());
        }

        ScriptQuery scriptQuery = tranData(scriptEntity);
        scriptQuery.setMediaDTO(mediaService.findByScriptId(id));
        return ResultGenerator.genSuccessResult(scriptQuery);
    }

    public ScriptQuery tranData(ScriptEntity scriptEntity){
        Gson gson = new Gson();
        List<String> tips = gson.fromJson(scriptEntity.getTips(), new TypeToken<List<String>>() {}.getType());
        List<String> goals = gson.fromJson(scriptEntity.getGoal(), new TypeToken<List<String>>() {}.getType());
        List<String> preambles = gson.fromJson(scriptEntity.getPreamble(), new TypeToken<List<String>>() {}.getType());

        ScriptQuery scriptQuery = new ScriptQuery();
        BeanUtils.copyProperties(scriptEntity,scriptQuery);
        scriptQuery.setTips(tips);
        scriptQuery.setGoal(goals);
        scriptQuery.setPreamble(preambles);
        return scriptQuery;
    }
    @ApiOperation(value = "新增一筆劇本資料")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request")
    })
    @PostMapping
    public Result save(@RequestBody ScriptDTO scriptDTO, BindingResult bindingResult) throws JsonProcessingException {
        MemberEntity member = JwtUtils.getMember();
        scriptDTO.setAuthor(member.getEmail());
        if (bindingResult.hasErrors()) {
            String errorMsg = bindingResult.getFieldErrors().stream()
                    .map(error -> error.getField() + " " + error.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            return ResultGenerator.genFailResult(errorMsg);
        }
        ScriptEntity scriptEntity = scriptService.save(scriptDTO);
        ScriptQuery scriptQuery = tranData(scriptEntity);

        return ResultGenerator.genSuccessResult(scriptQuery);
    }

    @ApiOperation(value = "新增每日劇本詳情資料")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request")
    })
    @PostMapping("/detail")
    public Result detailSave(@RequestBody ScriptDetailDTO scriptDetailDTO, BindingResult bindingResult) throws JsonProcessingException {
        if (bindingResult.hasErrors()) {
            String errorMsg = bindingResult.getFieldErrors().stream()
                    .map(error -> error.getField() + " " + error.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            return ResultGenerator.genFailResult(errorMsg);
        }
        // 判斷詳情天數是否大於原先的script設定
        ScriptDetailEntity scriptDetailEntity = scriptDetailService.findByPeriod(
                scriptDetailDTO.getScriptId(),scriptDetailDTO.getPeriod());

        ScriptEntity scriptEntity = scriptService.findOne(scriptDetailDTO.getScriptId());
        if (scriptEntity != null){
            if (scriptEntity.getScriptPeriod() - scriptDetailDTO.getPeriod() < 0) {
                return ResultGenerator.genFailResult(ResultCode.ADD_FAILD.getMessage());
            }
        }

        if (scriptDetailEntity == null){
            ScriptDetailEntity entity = scriptDetailService.save(scriptDetailDTO);
            parentConfigService.batchSave(scriptDetailDTO.getParentConfigs(), entity.getScriptDetailId());
        } else {
            List<ParentConfiglEntity> parentConfiglEntityList = parentConfigService.findByScriptDetailId(scriptDetailEntity.getScriptDetailId());
            List<ParentConfiglDTO> parentConfiglDTOList = scriptDetailDTO.getParentConfigs();

            // 編輯家長設定
            saveParentConfig(parentConfiglEntityList, parentConfiglDTOList, scriptDetailEntity.getScriptDetailId());
        }

        return ResultGenerator.genSuccessResult(ResultCode.ADD_SUCCESS.getMessage());
    }

    public void saveParentConfig(List<ParentConfiglEntity> parentConfiglEntityList, List<ParentConfiglDTO> parentConfiglDTOList, Integer scriptDetailId){
        for (int i = 0; i < parentConfiglDTOList.size(); i++) {
            ParentConfiglDTO dto = parentConfiglDTOList.get(i);
            dto.setScriptDetailId(scriptDetailId);
            boolean found = false;

            for (ParentConfiglEntity entity : parentConfiglEntityList) {
                if (entity.getId().equals(dto.getId())) { // 假設有一個 getId() 方法用於取得唯一識別標誌
                    parentConfigService.update(dto);
                    found = true;
                    break;
                }
            }

            if (!found) {
                parentConfigService.save(dto);
            }
        }
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
        log.info("[[更改劇本資料]] id:{}, dto:{}", id, scriptDTO);
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
            @ApiImplicitParam(name = "description", value = "圖片位置說明", required = true, dataType = "String"),
            @ApiImplicitParam(name = "file", value = "文件", required = true, dataType = "MultipartFile"),

    })
    @PostMapping("/upload/{scriptId}")
    @ResponseBody
    @Transactional
    public Result handleFileUpload(@PathVariable Integer scriptId,
                                   @RequestParam("description") String description,
                                   @RequestParam("file") MultipartFile file) throws IOException {
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
        MediaDTO mediaDTO = mediaService.findByScriptIdAndDescription(scriptId, description);
        if (mediaDTO!=null) {
            // 取得最後一個字節獲取storage的object_name
            String[] str = mediaDTO.getFilePath().split(File.separator);
            String lastByte = str[str.length - 1];
            fileService.removeFile(lastByte);
            mediaDTO.setFilePath(filePath);
            mediaDTO.setMediaType(mediaType);
            mediaDTO.setDescription(description);
            mediaService.update(mediaDTO);
        } else {
            mediaDTO = new MediaDTO();
            mediaDTO.setScriptId(scriptId);
            mediaDTO.setFilePath(filePath);
            mediaDTO.setMediaType(mediaType);
            mediaDTO.setDescription(description);
            mediaService.save(mediaDTO);
        }
        return ResultGenerator.genSuccessResult(ResultCode.UPLOAD_SUCCESS.getMessage());
    }

    @ApiOperation(value = "刪除檔案", notes = "刪除檔案接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "scriptId", value = "劇本ID", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "description", value = "影音ID", required = true, dataType = "String")
    })
    @DeleteMapping("/media/{scriptId}/{description}")
    @ResponseBody
    @Transactional
    public Result handleFileDelete(@PathVariable Integer scriptId, @PathVariable String description) throws IOException {

        MediaDTO mediaDTO = mediaService.findByScriptIdAndDescription(scriptId, description);
        if (mediaDTO == null) {
            return ResultGenerator.genSuccessResult(ResultCode.MATERIAL_INFO_NOT_EXIST.getMessage());
        }
        // 取得最後一個字節獲取storage的object_name
        String[] str = mediaDTO.getFilePath().split(File.separator);
        String lastByte = str[str.length - 1];

        fileService.removeFile(lastByte);
        mediaService.delete(scriptId, description);
        return ResultGenerator.genSuccessResult(ResultCode.DELETE_SUCCESS.getMessage());
    }
}
