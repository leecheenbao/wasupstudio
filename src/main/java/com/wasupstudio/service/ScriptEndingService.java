package com.wasupstudio.service;

import com.wasupstudio.model.BasePageInfo;
import com.wasupstudio.model.dto.ScriptEndingDTO;
import com.wasupstudio.model.entity.ScriptEndingEntity;

public interface ScriptEndingService {

    void save (ScriptEndingDTO scriptDTO);

    ScriptEndingDTO findOne(Integer id);

    BasePageInfo findAllData();

    void update(ScriptEndingDTO scriptDTO);

}
