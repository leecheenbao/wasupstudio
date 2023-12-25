package com.wasupstudio.service.Impl;

import com.wasupstudio.converter.MediaConverter;
import com.wasupstudio.enums.ScriptEndingEnum;
import com.wasupstudio.mapper.MediaMapper;
import com.wasupstudio.model.BasePageInfo;
import com.wasupstudio.model.dto.MediaDTO;
import com.wasupstudio.model.entity.MediaEntity;
import com.wasupstudio.model.entity.TaskEntity;
import com.wasupstudio.model.query.ScoreDistributionQuery;
import com.wasupstudio.service.AbstractService;
import com.wasupstudio.service.MediaService;
import com.wasupstudio.service.ScriptQuestionService;
import com.wasupstudio.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MediaServiceImpl extends AbstractService<MediaEntity> implements MediaService {
    @Autowired
    private MediaMapper mediaMapper;
    @Autowired
    private MediaConverter mediaConverter;

    @Autowired
    private ScriptQuestionService scriptQuestionService;
    @Autowired
    private TaskService taskService;

    @Override
    public void save(MediaDTO mediaDTO) {
        // TODO 還要實作上傳功能
        MediaEntity mediaEntity = new MediaEntity();
        mediaEntity.setScriptId(mediaDTO.getScriptId());
        mediaEntity.setMediaType(mediaDTO.getMediaType());
        mediaEntity.setFilePath(mediaDTO.getFilePath());
        mediaEntity.setCreateTime(new Date());
        mediaEntity.setUpdateTime(new Date());
        mediaEntity.setDescription(mediaDTO.getDescription());
        mediaEntity.setFileExtension(mediaDTO.getFileExtension());
        mediaEntity.setFilename(mediaDTO.getFilename());
        save(mediaEntity);
    }

    @Override
    public MediaEntity findOne(Integer id) {
        return this.findById(id);
    }

    @Override
    public List<MediaDTO> findByScriptId(Integer scriptId) {
        return mediaConverter.itemsToDtos(mediaMapper.findByScriptId(scriptId));
    }

    @Override
    public MediaDTO findByScriptIdAndDescription(Integer scriptId, String description) {
        MediaEntity mediaEntity = mediaMapper.findByScriptIdAndDescription(scriptId, description);
        if (mediaEntity !=null){
            return mediaConverter.itemToDto(mediaEntity);
        }
        return null;
    }

    @Override
    public MediaDTO scriptEndingFile(Integer taskId) {
        BasePageInfo basePageInfo = scriptQuestionService.scoreDistribution(taskId);

        TaskEntity task = taskService.findOne(taskId);
        List<ScoreDistributionQuery> list = basePageInfo.getList();

        Integer scritpId = task.getScriptId();
        String result = ScriptEndingEnum.SCRIPT_ENDING_ONE.getDesc();

        for (ScoreDistributionQuery item : list){
            scritpId = item.getScriptId();
            result = item.getResult();
        }
        String desc = ScriptEndingEnum.getType(result).getType();
        MediaDTO byScriptIdAndDescription = findByScriptIdAndDescription(scritpId, desc);
        return byScriptIdAndDescription;
    }

    @Override
    public BasePageInfo findAllData() {
        List<MediaEntity> list = this.findAll();
        BasePageInfo basePageInfo = new BasePageInfo<>();
        basePageInfo.setList(list);
        basePageInfo.setTotal(list.size());
        return basePageInfo;
    }

    @Override
    public void update(MediaDTO mediaDTO) {
        mediaMapper.updateByScriptIdAndDescription(
                mediaDTO.getMediaType(), new Date(), mediaDTO.getFileExtension(),
                mediaDTO.getScriptId(), mediaDTO.getDescription(), mediaDTO.getFilePath(), mediaDTO.getFilename()
        );
    }

    @Override
    public void delete(Integer scriptId, String description) {
        mediaMapper.deleteByStringIdAndDescription(scriptId, description);
    }
}
