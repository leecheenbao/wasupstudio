package com.wasupstudio.service.Impl;

import com.wasupstudio.model.BasePageInfo;
import com.wasupstudio.model.dto.StudentConfigDTO;
import com.wasupstudio.model.entity.ParentConfiglEntity;
import com.wasupstudio.model.entity.StudentConfiglEntity;
import com.wasupstudio.service.AbstractService;
import com.wasupstudio.service.ParentConfigService;
import com.wasupstudio.service.StudentConfigService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentConfigServiceImpl extends AbstractService<StudentConfiglEntity> implements StudentConfigService {

    @Override
    public void save(StudentConfiglEntity entity) {
        this.save(entity);
    }

    @Override
    public void batchSave(List<StudentConfigDTO> list) {

    }

    @Override
    public StudentConfiglEntity findOne(Integer id) {
        return this.findById(id);
    }

    @Override
    public BasePageInfo findAllData() {
        List<StudentConfiglEntity> list = this.findAll();
        BasePageInfo basePageInfo = new BasePageInfo<>();
        basePageInfo.setList(list);
        basePageInfo.setTotal(list.size());
        return basePageInfo;
    }

    @Override
    public void update(StudentConfiglEntity entity) {
        this.save(entity);
    }
}
