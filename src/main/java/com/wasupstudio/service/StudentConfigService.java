package com.wasupstudio.service;

import com.wasupstudio.model.BasePageInfo;
import com.wasupstudio.model.entity.StudentConfiglEntity;

public interface StudentConfigService {

    void save (StudentConfiglEntity entity);

    StudentConfiglEntity findOne(Integer id);

    BasePageInfo findAllData();

    void update(StudentConfiglEntity entity);

}
