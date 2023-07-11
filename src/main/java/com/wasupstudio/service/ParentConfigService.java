package com.wasupstudio.service;

import com.wasupstudio.model.BasePageInfo;
import com.wasupstudio.model.entity.ParentConfiglEntity;

public interface ParentConfigService {

    void save (ParentConfiglEntity entity);

    ParentConfiglEntity findOne(Integer id);

    BasePageInfo findAllData();

    void update(ParentConfiglEntity entity);

}
