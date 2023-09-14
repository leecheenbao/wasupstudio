package com.wasupstudio.service;

import com.wasupstudio.model.BasePageInfo;
import com.wasupstudio.model.dto.ScriptQuestionDTO;
import com.wasupstudio.model.entity.ScriptQuestionEntity;

public interface ScriptQuestionOptionService {

    void save(ScriptQuestionDTO dto);

    void delete(Integer questionId);

    BasePageInfo<ScriptQuestionEntity> findByTaskId(Integer taskId);

    BasePageInfo<ScriptQuestionEntity> findAllData();

    void update(ScriptQuestionDTO dto);

}
