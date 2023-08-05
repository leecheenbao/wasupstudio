package com.wasupstudio.service;

import com.wasupstudio.model.BasePageInfo;
import com.wasupstudio.model.dto.StudentConfigDTO;
import com.wasupstudio.model.entity.StudentConfiglEntity;

import java.util.List;

public interface StudentConfigService {

    void save (StudentConfigDTO dto);

    void batchSave(List<StudentConfigDTO> list, Integer scriptDetailId);

    StudentConfiglEntity findOne(Integer id);

    List<StudentConfiglEntity> findByScriptDetailId(Integer scriptDetailId);

    BasePageInfo findAllData();

    void update(StudentConfigDTO dto);

}
