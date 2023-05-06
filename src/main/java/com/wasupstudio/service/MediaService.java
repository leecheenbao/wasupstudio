package com.wasupstudio.service;

import com.wasupstudio.model.BasePageInfo;
import com.wasupstudio.model.dto.MediaDTO;
import com.wasupstudio.model.entity.MediaEntity;

import java.util.List;

public interface MediaService {

    void save (MediaDTO mediaDTO);

    MediaEntity findOne(Integer id);

    List<MediaDTO> findByScriptId(Integer scriptId);

    BasePageInfo findAllData();

    void update(MediaDTO mediaDTO);

}
