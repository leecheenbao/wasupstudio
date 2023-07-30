package com.wasupstudio.service;

import com.wasupstudio.model.BasePageInfo;
import com.wasupstudio.model.dto.StudentConfigDTO;
import com.wasupstudio.model.entity.StudentConfiglEntity;

import java.util.List;

public interface StudentConfigService {

    void save (StudentConfiglEntity entity);

    void batchSave(List<StudentConfigDTO> list);

    StudentConfiglEntity findOne(Integer id);

    BasePageInfo findAllData();

    void update(StudentConfiglEntity entity);

}
