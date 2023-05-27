package com.wasupstudio.service;

import com.wasupstudio.model.BasePageInfo;
import com.wasupstudio.model.dto.ScriptDetailDTO;
import com.wasupstudio.model.entity.ScriptDetailEntity;

public interface ScriptDetailService {

    void save (ScriptDetailDTO entity);

    ScriptDetailEntity findOne(Integer id);

    BasePageInfo findAllData();

    void update(ScriptDetailDTO entity);

}
