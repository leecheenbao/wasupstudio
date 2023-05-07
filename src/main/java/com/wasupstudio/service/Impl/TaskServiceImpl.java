package com.wasupstudio.service.Impl;

import com.wasupstudio.mapper.ScriptMapper;
import com.wasupstudio.mapper.TaskMapper;
import com.wasupstudio.model.BasePageInfo;
import com.wasupstudio.model.dto.ScriptDTO;
import com.wasupstudio.model.dto.TaskDTO;
import com.wasupstudio.model.entity.ScriptEntity;
import com.wasupstudio.model.entity.TaskEntity;
import com.wasupstudio.service.AbstractService;
import com.wasupstudio.service.ScriptService;
import com.wasupstudio.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TaskServiceImpl extends AbstractService<TaskEntity> implements TaskService {
    @Autowired
    public TaskMapper taskMapper;

    @Override
    public void save(TaskDTO taskDTO) {

        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setTaskName(taskDTO.getTaskName());
        taskEntity.setPriority(taskDTO.getPriority());
        taskEntity.setDescription(taskDTO.getDescription());
        taskEntity.setAuthor(taskDTO.getAuthor());
        taskEntity.setEstimatedParticipants(taskDTO.getEstimatedParticipants());
        taskEntity.setStatus(taskDTO.getStatus());
        taskEntity.setCreateTime(new Date());
        taskEntity.setUpdateTime(new Date());
        save(taskEntity);
    }

    @Override
    public TaskEntity findOne(Integer id) {
        return this.findById(id);
    }

    @Override
    public TaskEntity getScriptByTitle(String title) {

        return null;
    }

    @Override
    public BasePageInfo findAllData() {

        List<TaskEntity> list = this.findAll();
        BasePageInfo basePageInfo = new BasePageInfo<>();
        basePageInfo.setList(list);
        basePageInfo.setTotal(list.size());
        return basePageInfo;
    }

    @Override
    public void update(TaskDTO taskDTO) {
        TaskEntity taskEntity = this.findOne(taskDTO.getTaskId());
        if (taskEntity != null){
            taskEntity.setTaskName(taskDTO.getTaskName());
            taskEntity.setPriority(taskDTO.getPriority());
            taskEntity.setDescription(taskDTO.getDescription());
            taskEntity.setEstimatedParticipants(taskDTO.getEstimatedParticipants());
            taskEntity.setAuthor(taskDTO.getAuthor());
            taskEntity.setStatus(taskDTO.getStatus());
            taskEntity.setUpdateTime(new Date());

            this.update(taskEntity);
        }
    }
}
