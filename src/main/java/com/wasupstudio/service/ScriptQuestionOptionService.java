package com.wasupstudio.service;

import com.wasupstudio.model.BasePageInfo;
import com.wasupstudio.model.dto.ScriptQuestionDTO;
import com.wasupstudio.model.entity.ScriptQuestionEntity;

import java.util.List;

public interface ScriptQuestionOptionService {

    void save(ScriptQuestionDTO dto);

    BasePageInfo<ScriptQuestionEntity> findByTaskId(Integer taskId);

    BasePageInfo<ScriptQuestionEntity> findAllData();

    void update(ScriptQuestionDTO dto);

}
