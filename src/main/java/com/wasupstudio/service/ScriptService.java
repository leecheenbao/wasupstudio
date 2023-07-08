package com.wasupstudio.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wasupstudio.model.BasePageInfo;
import com.wasupstudio.model.dto.ScriptDTO;
import com.wasupstudio.model.entity.ScriptEntity;

public interface ScriptService {

    void save (ScriptDTO scriptDTO);

    ScriptEntity findOne(Integer id);

    BasePageInfo findAllData();

    void update(ScriptDTO scriptDTO);

}
