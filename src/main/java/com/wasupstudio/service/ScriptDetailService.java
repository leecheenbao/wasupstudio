package com.wasupstudio.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wasupstudio.model.BasePageInfo;
import com.wasupstudio.model.dto.ScriptDetailDTO;
import com.wasupstudio.model.entity.ScriptDetailEntity;

public interface ScriptDetailService {

    ScriptDetailEntity save (ScriptDetailDTO entity) throws JsonProcessingException;

    ScriptDetailEntity findOne(Integer id);

    ScriptDetailEntity findByPeriod(Integer scripdDetailId, Integer period);

    BasePageInfo findAllData();

    void update(ScriptDetailDTO entity) throws JsonProcessingException;

}
