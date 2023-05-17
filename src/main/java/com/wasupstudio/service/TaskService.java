package com.wasupstudio.service;

import com.wasupstudio.model.BasePageInfo;
import com.wasupstudio.model.dto.TaskDTO;
import com.wasupstudio.model.entity.TaskEntity;

public interface TaskService {

    void save (TaskDTO taskDTO);

    TaskEntity findOne(Integer id);

    BasePageInfo findAllData();

    BasePageInfo findMyTask(Integer memberId);

    void update(TaskDTO taskDTO);

}
