package com.wasupstudio.service.Impl;

import com.wasupstudio.converter.MediaConverter;
import com.wasupstudio.mapper.MediaMapper;
import com.wasupstudio.model.BasePageInfo;
import com.wasupstudio.model.dto.MediaDTO;
import com.wasupstudio.model.entity.MediaEntity;
import com.wasupstudio.service.AbstractService;
import com.wasupstudio.service.MediaService;
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

    @Override
    public void save(MediaDTO mediaDTO) {
        // TODO 還要實作上傳功能
        MediaEntity mediaEntity = new MediaEntity();
        mediaEntity.setScriptId(mediaDTO.getScriptId());
        mediaEntity.setMediaType(mediaDTO.getMediaType());
        mediaEntity.setFilePath(mediaDTO.getFilePath());
        mediaEntity.setUpdateTime(new Date());
        mediaEntity.setUpdateTime(new Date());
        save(mediaEntity);
    }

    @Override
    public MediaEntity findOne(Integer id) {
        return this.findById(id);
    }

    @Override
    public List<MediaDTO> findByScriptId(Integer scriptId) {
        return mediaConverter.map(mediaMapper.findByScriptId(scriptId));
    }

    @Override
    public MediaDTO findByScriptIdAndMediaId(Integer scriptId, Integer mediaId) {
        MediaEntity mediaEntity = mediaMapper.findByScriptIdAndMediaId(scriptId, mediaId);
        if (mediaEntity !=null){
            return mediaConverter.map(mediaEntity);
        }
        return null;
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

    }

    @Override
    public void delete(Integer scriptId, Integer mediaId) {
        deleteById(mediaId);
    }
}
