package com.wasupstudio.service;

import com.wasupstudio.model.BasePageInfo;
import com.wasupstudio.model.dto.ParentConfiglDTO;
import com.wasupstudio.model.entity.ParentConfiglEntity;

import java.util.List;

public interface ParentConfigService {

    void save (ParentConfiglEntity entity);

    void batchSave(List<ParentConfiglDTO> list);

    ParentConfiglEntity findOne(Integer id);

    BasePageInfo findAllData();

    void update(ParentConfiglEntity entity);

}
