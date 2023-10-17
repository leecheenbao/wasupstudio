package com.wasupstudio.service;

import com.wasupstudio.model.BasePageInfo;
import com.wasupstudio.model.dto.ParentConfiglDTO;
import com.wasupstudio.model.entity.ParentConfiglEntity;

import java.util.List;

public interface ParentConfigService {

    void save (ParentConfiglDTO dto);

    void deleteByScriptDetailId(Integer scriptDetailId);

    void batchSave(List<ParentConfiglDTO> list, Integer scriptDetailId);

    void batchUpdate(List<ParentConfiglDTO> list, Integer scriptDetailId);

    ParentConfiglEntity findOne(Integer id);

    List<ParentConfiglEntity> findByScriptDetailId(Integer scriptDetailId);
    BasePageInfo findAllData();

    void update(ParentConfiglDTO dto);

}
