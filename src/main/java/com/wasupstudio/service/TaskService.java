package com.wasupstudio.service;

import com.wasupstudio.model.BasePageInfo;
import com.wasupstudio.model.dto.ScriptDTO;
import com.wasupstudio.model.dto.TaskDTO;
import com.wasupstudio.model.entity.ScriptEntity;
import com.wasupstudio.model.entity.TaskEntity;

public interface TaskService {

    void save (TaskDTO taskDTO);

    TaskEntity findOne(Integer id);

    TaskEntity getScriptByTitle(String title);

    BasePageInfo findAllData();

    void update(TaskDTO taskDTO);

}
