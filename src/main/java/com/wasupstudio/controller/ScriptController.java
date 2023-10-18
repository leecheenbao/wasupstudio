package com.wasupstudio.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.wasupstudio.constant.BaseRedisKeyConstant;
import com.wasupstudio.enums.FileTypeEnum;
import com.wasupstudio.enums.ResultCode;
import com.wasupstudio.exception.ResultGenerator;
import com.wasupstudio.model.BasePageInfo;
import com.wasupstudio.model.Result;
import com.wasupstudio.model.dto.*;
import com.wasupstudio.model.entity.*;
import com.wasupstudio.model.query.ScriptQuery;
import com.wasupstudio.service.*;
import com.wasupstudio.util.*;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.wasupstudio.util.FileUtils.getFileExtension;

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
    private ScriptEndingService scriptEndingService;
    @Autowired
    private MediaService mediaService;
    @Autowired
    private FileService fileService;
    @Autowired
    private RedisUtil redisUtil;

    @ApiOperation(value = "取得劇本資料")
    @GetMapping
    public Result getAllData() {
        BasePageInfo pageInfo = scriptService.findAllData();
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @ApiOperation(value = "新增一筆劇本資料")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request")
    })
    @PostMapping
    public Result save(@RequestBody ScriptDTO scriptDTO) throws JsonProcessingException {
        MemberEntity member = JwtUtils.getMember();
        scriptDTO.setAuthor(member.getEmail());

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
    public Result detailSave(@RequestBody ScriptDetailDTO scriptDetailDTO) throws JsonProcessingException {
        // 判斷詳情天數是否大於原先的script設定
        ScriptEntity scriptEntity = scriptService.findOne(scriptDetailDTO.getScriptId());
        if (scriptEntity != null) {
            if (scriptEntity.getScriptPeriod() - scriptDetailDTO.getPeriod() < 0) {
                return ResultGenerator.genFailResult(ResultCode.ADD_FAILD.getMessage());
            }
        }

        ScriptDetailEntity scriptDetailEntity = scriptDetailService.findByPeriod(
                scriptDetailDTO.getScriptId(), scriptDetailDTO.getPeriod());
        studentConfigService.deleteByScriptDetailId(scriptDetailEntity.getScriptDetailId());
        parentConfigService.deleteByScriptDetailId(scriptDetailEntity.getScriptDetailId());

        if (scriptDetailEntity == null) {
            ScriptDetailEntity entity = scriptDetailService.save(scriptDetailDTO);
            parentConfigService.batchSave(scriptDetailDTO.getParentConfigs(), entity.getScriptDetailId());
            studentConfigService.batchSave(scriptDetailDTO.getStudentConfigs(), entity.getScriptDetailId());
        } else {
            scriptDetailService.update(scriptDetailDTO);

            List<ParentConfiglEntity> parentConfiglEntityList = parentConfigService.findByScriptDetailId(scriptDetailEntity.getScriptDetailId());
            List<StudentConfiglEntity> studentConfiglEntityList = studentConfigService.findByScriptDetailId(scriptDetailEntity.getScriptDetailId());
            List<ParentConfiglDTO> parentConfiglDTOList = scriptDetailDTO.getParentConfigs();
            List<StudentConfigDTO> studentConfigDTOList = scriptDetailDTO.getStudentConfigs();
            // 編輯學生設定
            if (studentConfigDTOList != null) {
                saveStudentConfig(studentConfiglEntityList, studentConfigDTOList, scriptDetailEntity.getScriptDetailId());
            }
            // 編輯家長設定
            if (parentConfiglDTOList != null) {
                saveParentConfig(parentConfiglEntityList, parentConfiglDTOList, scriptDetailEntity.getScriptDetailId());
            }
        }

        return ResultGenerator.genSuccessResult(ResultCode.UPDATE_SUCCESS.getMessage());
    }

    @ApiOperation(value = "新增劇本結局資料")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request")
    })
    @PostMapping("/ending")
    public Result enddingSave(@RequestBody ScriptEndingDTO scriptEndingDTO) {
        ScriptEntity scriptEntity = scriptService.findOne(scriptEndingDTO.getScriptId());
        if (scriptEntity == null) {
            return ResultGenerator.genFailResult(ResultCode.ADD_FAILD.getMessage());
        }

        scriptEndingService.save(scriptEndingDTO);

        return ResultGenerator.genSuccessResult(ResultCode.ADD_SUCCESS.getMessage());
    }

    public void saveParentConfig(List<ParentConfiglEntity> parentConfiglEntityList, List<ParentConfiglDTO> parentConfiglDTOList, Integer scriptDetailId) {
        for (ParentConfiglDTO dto : parentConfiglDTOList) {
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

    public void saveStudentConfig(List<StudentConfiglEntity> studentConfiglEntityList, List<StudentConfigDTO> studentConfigDTOList, Integer scriptDetailId) {
        for (StudentConfigDTO dto : studentConfigDTOList) {
            dto.setScriptDetailId(scriptDetailId);
            boolean found = false;

            for (StudentConfiglEntity entity : studentConfiglEntityList) {
                if (entity.getId().equals(dto.getId())) { // 假設有一個 getId() 方法用於取得唯一識別標誌
                    studentConfigService.update(dto);
                    found = true;
                    break;
                }
            }

            if (!found) {
                studentConfigService.save(dto);
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
    public Result update(@PathVariable Integer id, @RequestBody ScriptDTO scriptDTO) {
        MemberEntity member = JwtUtils.getMember();
        scriptDTO.setAuthor(member.getEmail());
        scriptDTO.setScriptId(id);
        scriptService.update(scriptDTO);
        return ResultGenerator.genSuccessResult(ResultCode.UPDATE_SUCCESS.getMessage());
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

        if (FileUtils.validateFileExtension(file.getOriginalFilename())) {
            return ResultGenerator.genSuccessResult((ResultCode.UPLOAD_FORMAT_ERROR.getMessage()));
        }
        if (FileUtils.validateFileSize(file)) {
            String type = FileTypeEnum.getEnum(FileUtils.checkFileType(file.getOriginalFilename())).getDesc();
            String size = String.valueOf(FileUtils.MAX_FILE_SIZE);
            return ResultGenerator.genSuccessResult(ResultCode.UPLOAD_MAX_ERROR.getFormattedMessage(type, size));
        }

        String originalFileName = file.getOriginalFilename();
        String fileName = DateUtils.currentTimeMillis() + "." + getFileExtension(file.getOriginalFilename());
        String mediaType = FileUtils.checkFileType(fileName);

        String filePath = fileService.uploadFile(file.getBytes(), fileName, mediaType);
        MediaDTO mediaDTO = mediaService.findByScriptIdAndDescription(scriptId, description);
        String extension = getFileExtension(Objects.requireNonNull(file.getOriginalFilename()));
        if (mediaDTO != null) {
            // 取得最後一個字節獲取storage的object_name
            String[] str = mediaDTO.getFilePath().split(File.separator);
            String oldFile = str[str.length - 1];
            fileService.removeFile(oldFile);
            mediaDTO.setScriptId(scriptId);
            mediaDTO.setFilePath(filePath);
            mediaDTO.setMediaType(mediaType);
            mediaDTO.setDescription(description);
            mediaDTO.setFileExtension(extension);
            mediaDTO.setFilename(originalFileName);
            mediaService.update(mediaDTO);
        } else {
            mediaDTO = new MediaDTO();
            mediaDTO.setScriptId(scriptId);
            mediaDTO.setFilePath(filePath);
            mediaDTO.setMediaType(mediaType);
            mediaDTO.setDescription(description);
            mediaDTO.setFileExtension(extension);
            mediaDTO.setFilename(originalFileName);
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


    @ApiOperation(value = "取得單一劇本資料")
    @ApiImplicitParam(name = "scriptId", value = "scriptId", required = true, dataType = "int", paramType = "path")
    @GetMapping("/{scriptId}")
    public Result getOneData(@PathVariable Integer scriptId) {
        ScriptEntity scriptEntity = scriptService.findOne(scriptId);
        if (scriptEntity == null) {
            return ResultGenerator.genSuccessResult(ResultCode.DATA_NOT_EXIST.getMessage());
        }
        // 劇本建議天數(扣掉結局日)
        int period = scriptEntity.getScriptPeriod() - 1;

        List<ScriptDetailDTO> detailDTOS = scriptDetailService.findByScriptId(scriptId);
        List<ScriptDetailDTO> details = new ArrayList<>();
        for (int count = 1; count <= period; count++) {
            boolean found = false;
            for (ScriptDetailDTO dto : detailDTOS) {
                if (dto.getPeriod().equals(count)) {
                    // 如果在 detailDTOS 中找到了該期數的資料，則將它添加到 details 中
                    details.add(dto);
                    found = true;
                    break;
                }
            }
            if (!found) {
                // 如果在 detailDTOS 中未找到該期數的資料，則添加一個空的物件到 details 中
                ScriptDetailDTO emptyDto = new ScriptDetailDTO();
                emptyDto.setPeriod(count);
                // 可以根據需求設定其他屬性為預設值
                details.add(emptyDto);
            }
        }

        ScriptEndingDTO scriptEndingDTO = scriptEndingService.findOne(scriptId);
        ScriptQuery scriptQuery = tranData(scriptEntity);
        scriptQuery.setMediaDTO(mediaService.findByScriptId(scriptId));
        scriptQuery.setScriptDetail(details);
        scriptQuery.setScriptEndingDTO(scriptEndingDTO);
        return ResultGenerator.genSuccessResult(scriptQuery);
    }

    @ApiOperation(value = "PDF檔案下載")
    @PostMapping("/download/pdf")
    @ResponseBody
    public Result downloadMixFile(@RequestBody FileDownloadDTO fileDownloadDTO) {
        FileUtils fileUtils = new FileUtils();
        MediaDTO pdf = mediaService.findByScriptIdAndDescription(fileDownloadDTO.getScriptId(), fileDownloadDTO.getSheet());
        MediaDTO media = mediaService.findByScriptIdAndDescription(fileDownloadDTO.getScriptId(), fileDownloadDTO.getMedia());
        MemberEntity member = JwtUtils.getMember();
        Integer memberId = member.getId();

        String filePath = redisUtil.getKey(getRedisKey(memberId, fileDownloadDTO));
        if (filePath != null){
            log.info("取得快取PDF路徑 {}", filePath);
            return ResultGenerator.genSuccessResult(filePath);
        }

        if (pdf == null) {
            return ResultGenerator.genFailResult(ResultCode.DATA_NOT_EXIST.getMessage());
        }

        if (!pdf.getFileExtension().equals("pdf")) {
            return ResultGenerator.genFailResult(ResultCode.UPLOAD_FORMAT_ERROR.getMessage());
        }

        String pdfFilePath = pdf.getFilePath();
        String mediaFilePath = media.getFilePath();

        try {
            String fileName = AesUtils.encrypt(member.getId() + "_" + pdf.getFilename()) + "." + pdf.getFileExtension();
            File cacheFile = fileUtils.generateCacheFile(fileName, mediaFilePath, pdfFilePath);
            if (cacheFile.exists()) {
                filePath = uploadCacheFile(cacheFile);
                log.info("delete cache file:{}", cacheFile.delete());
                // 儲存路徑到redis 5 min
                redisUtil.setExpire(BaseRedisKeyConstant.FILE_DOWNLOAD, filePath, 5, TimeUnit.MINUTES);

                return ResultGenerator.genSuccessResult(filePath);
            } else {
                return ResultGenerator.genFailResult(ResultCode.DATA_NOT_EXIST.getMessage());
            }
        } catch (IOException e) {
            return ResultGenerator.genFailResult("檔案處理出現錯誤：" + e.getMessage());
        }
    }

    private static String getRedisKey(Integer memberId, FileDownloadDTO fileDownloadDTO){
        if (fileDownloadDTO == null){
            return null;
        }
        String key = memberId + "_" + fileDownloadDTO.getScriptId() + "_" + fileDownloadDTO.getSheet();
        log.info("儲存路徑至 redis KEY:{}", key);
        return key;
    }

    private String uploadCacheFile(File cacheFile) {
        try {
            byte[] bytes = Files.readAllBytes(cacheFile.toPath());
            String mediaType = FileUtils.checkFileType(cacheFile.getName());
            return fileService.uploadFile(bytes, cacheFile.getName(), mediaType);
        } catch (IOException e) {
            // 處理上傳錯誤
            return null;
        }
    }

    public ScriptQuery tranData(ScriptEntity scriptEntity) {
        return getScriptQuery(scriptEntity);
    }

    public static ScriptQuery getScriptQuery(ScriptEntity scriptEntity) {
        Gson gson = new Gson();
        List<String> tips = gson.fromJson(scriptEntity.getTips(), List.class);
        List<String> goals = gson.fromJson(scriptEntity.getGoal(), List.class);
        List<String> preambles = gson.fromJson(scriptEntity.getPreamble(), List.class);
        ScriptQuery scriptQuery = new ScriptQuery();
        BeanUtils.copyProperties(scriptEntity, scriptQuery);
        scriptQuery.setTips(tips);
        scriptQuery.setGoal(goals);
        scriptQuery.setPreamble(preambles);
        return scriptQuery;
    }
}
