package com.wasupstudio.service;

import com.wasupstudio.model.BasePageInfo;
import com.wasupstudio.model.dto.ScriptQuestionDTO;
import com.wasupstudio.model.entity.ScriptQuestionEntity;

public interface ScriptQuestionService {

    void save(ScriptQuestionDTO dto);

    void delete(Integer questionId);

    BasePageInfo findByTaskId(Integer taskId);

    BasePageInfo findAllData();

    void update(ScriptQuestionDTO dto);

    BasePageInfo findReportForEnding();

    BasePageInfo scoreDistribution(Integer taskId);

    BasePageInfo scoreDistribution();

}
