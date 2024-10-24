package com.wasupstudio.service.Impl;

import com.wasupstudio.mapper.TaskMapper;
import com.wasupstudio.model.BasePageInfo;
import com.wasupstudio.model.dto.TaskDTO;
import com.wasupstudio.model.entity.TaskEntity;
import com.wasupstudio.service.AbstractService;
import com.wasupstudio.service.TaskService;
import com.wasupstudio.util.DateUtils;
import com.wasupstudio.util.ValueValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
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
        taskEntity.setMemberId(taskDTO.getMemberId());
        taskEntity.setScriptId(taskDTO.getScriptId());
        taskEntity.setEstimatedParticipants(taskDTO.getEstimatedParticipants());
        taskEntity.setStatus(taskDTO.getStatus());
        taskEntity.setCreateTime(new Date());

        Date endTime = DateUtils.parse(taskDTO.getEndTime(), DateUtils.YYYY_MM_DD_HH_MM_SS);
        endTime = DateUtils.getEndDate(endTime);
        log.info("endTime:{}, taskDTO:{}", endTime, taskDTO.getEndTime());

        taskEntity.setEndTime(endTime);
        taskEntity.setLearning(taskEntity.getLearning());

        save(taskEntity);
    }

    public static void main(String[] args) {
        Date endTime = DateUtils.parse("2023-01-19", DateUtils.YYYY_MM_DD_HH_MM_SS);

        System.out.println(DateUtils.getEndDate(endTime));
    }
    @Override
    public TaskEntity findOne(Integer id) {
        return this.findById(id);
    }

    @Override
    public BasePageInfo findAllData() {

        List<TaskEntity> list = taskMapper.findAllData();

        BasePageInfo basePageInfo = new BasePageInfo<>();
        basePageInfo.setList(list);
        basePageInfo.setTotal(list.size());
        return basePageInfo;
    }

    @Override
    public BasePageInfo findMyTask(Integer memberId) {
        List<TaskEntity> taskEntityList = taskMapper.getMyTask(memberId);
        BasePageInfo basePageInfo = new BasePageInfo<>();
        basePageInfo.setList(taskEntityList);
        basePageInfo.setTotal(taskEntityList.size());

        return basePageInfo;
    }

    @Override
    public void update(TaskDTO taskDTO) {
        TaskEntity taskEntity = this.findOne(taskDTO.getTaskId());
        if (taskEntity != null) {
            taskEntity.setAuthor(taskDTO.getAuthor());
            taskEntity.setMemberId(taskDTO.getMemberId());

            if (!ValueValidator.isNullOrEmpty(taskDTO.getTaskName())) {
                taskEntity.setTaskName(taskDTO.getTaskName());
            }
            if (!ValueValidator.isNullOrZero(taskDTO.getPriority())) {
                taskEntity.setPriority(taskDTO.getPriority());
            }
            if (!ValueValidator.isNullOrEmpty(taskDTO.getDescription())) {
                taskEntity.setDescription(taskDTO.getDescription());
            }
            if (!ValueValidator.isNullOrZero(taskDTO.getScriptId())) {
                taskEntity.setScriptId(taskDTO.getScriptId());
            }
            if (!ValueValidator.isNullOrZero(taskDTO.getEstimatedParticipants())) {
                taskEntity.setEstimatedParticipants(taskDTO.getEstimatedParticipants());
            }
            if (!ValueValidator.isNullOrZero(taskDTO.getStatus())) {
                taskEntity.setStatus(taskDTO.getStatus());
            }
            if (!ValueValidator.isNullOrEmpty(taskDTO.getEndTime())) {
                Date endTime = DateUtils.parse(taskDTO.getEndTime(), DateUtils.YYYY_MM_DD_HH_MM_SS);
                endTime = DateUtils.getEndDate(endTime);
                log.info("endTime:{}, taskDTO:{}", endTime, taskDTO.getEndTime());

                taskEntity.setEndTime(endTime);
            }
            if (!ValueValidator.isNullOrZero(taskDTO.getLearning())) {
                taskEntity.setLearning(taskDTO.getLearning());
            }
            this.update(taskEntity);
        }
    }
}


