package com.wasupstudio.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.wasupstudio.constant.BaseRedisKeyConstant;
import com.wasupstudio.enums.FileTypeEnum;
import com.wasupstudio.enums.ResultCode;
import com.wasupstudio.exception.BusinessException;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

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
    private TaskService taskService;
    @Autowired
    private MediaService mediaService;
    @Autowired
    private FileService fileService;
    @Autowired
    private RedisUtil redisUtil;

    @Value("${base.url}")
    private String BASE_URL;

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

        // 驗證檔案類型
        FileUtils.validateFileExtension(file.getOriginalFilename());

        // 檢查檔案大小
        FileUtils.validateFileSize(file);

        String originalFileName = file.getOriginalFilename();
        String fileName = DateUtils.currentTimeMillis() + "." + getFileExtension(Objects.requireNonNull(file.getOriginalFilename()));
        String mediaType = FileUtils.checkFileType(fileName);

        String filePath = fileService.uploadFile(file.getBytes(), fileName, mediaType);
        MediaDTO mediaDTO = mediaService.findByScriptIdAndDescription(scriptId, description);
        String extension = getFileExtension(Objects.requireNonNull(file.getOriginalFilename()));
        if (mediaDTO != null) {
            // 取得最後一個字節獲取storage的object_name
            String[] str = mediaDTO.getFilePath().split(File.separator);
            String oldFile = str[str.length - 1];
            fileService.removeFile(Objects.requireNonNull(FileTypeEnum.getEnum(mediaType)).getPath() + oldFile);
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

        fileService.removeFile(Objects.requireNonNull(FileTypeEnum.getEnum(mediaDTO.getMediaType())).getPath() + lastByte);
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
    @PostMapping(value = "/download/pdf")
    @ResponseBody
    public Result downloadMixFile(@RequestBody FileDownloadDTO fileDownloadDTO) {
        // 取得對應PDF及影片資料
        TaskEntity task = taskService.findOne(fileDownloadDTO.getTaskId());
        if (task == null) {
            return ResultGenerator.genFailResult(ResultCode.DATA_NOT_EXIST.getMessage());
        }
        MediaDTO pdf = mediaService.findByScriptIdAndDescription(task.getScriptId(), fileDownloadDTO.getSheet());
        if (pdf == null) {
            return ResultGenerator.genFailResult(ResultCode.DATA_NOT_EXIST.getMessage());
        }

        if (!pdf.getFileExtension().equals("pdf")) {
            return ResultGenerator.genFailResult(ResultCode.UPLOAD_FORMAT_ERROR.getMessage());
        }

        MemberEntity member = JwtUtils.getMember();
        Integer memberId = member.getId();
        if (!Objects.equals(memberId, task.getMemberId())) {
            return ResultGenerator.genFailResult(member.getName() + "與創建任務者不符");
        }
        // 取得對應會員的 redis id
        String redisKey = getRedisKey(memberId, fileDownloadDTO);
        String filePath = redisUtil.getKey(redisKey);
        if (filePath != null) {
            log.info("取得redis KEY:{}, PDF路徑 {}", redisKey, filePath);
            return ResultGenerator.genSuccessResult(filePath);
        }

        try {
            long time = calculateRemainingSeconds(task.getEndTime());

            String url = BASE_URL + "/auth/download/pdf/valid?";

            Map<String, Object> params = new TreeMap<>();
            params.put("taskId", task.getTaskId());
            params.put("media", fileDownloadDTO.getMedia());
            CashFlowUtils cashFlowUtils = new CashFlowUtils();
            String dataInfo = cashFlowUtils.getDataInfo(params);

            filePath = PdfWithQrCodeUtils.mixPdfAndQrCode(url + dataInfo, pdf.getFilePath());

            // 儲存路徑到redis 依照任務時間保存
            if (time > 0) {
                redisUtil.setExpire(redisKey, filePath, time);
            }

            return ResultGenerator.genSuccessResult(filePath);
        } catch (BusinessException e) {
            log.warn(e.getMessage());
            return ResultGenerator.genSuccessResult(pdf.getFilePath());
        }
    }

    public static long calculateRemainingSeconds(Date endTime) {
        // 將endTime轉換為LocalDateTime
        Instant instant = endTime.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime expirationTime = instant.atZone(zoneId).toLocalDateTime();
        LocalDateTime currentTime = LocalDateTime.now();
        // 計算當前時間到endTime的持續時間
        Duration duration = Duration.between(currentTime, expirationTime);
        // 獲取持續時間的秒數
        return duration.getSeconds();
    }


    private static String getRedisKey(Integer memberId, FileDownloadDTO fileDownloadDTO) {
        if (fileDownloadDTO == null) {
            return null;
        }
        return String.format(BaseRedisKeyConstant.FILE_DOWNLOAD, memberId, fileDownloadDTO.getTaskId() + "_" + fileDownloadDTO.getMedia());
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
