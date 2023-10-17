package com.wasupstudio.service.Impl;

import com.wasupstudio.converter.StudentConfigConverter;
import com.wasupstudio.mapper.StudentConfigMapper;
import com.wasupstudio.model.BasePageInfo;
import com.wasupstudio.model.dto.StudentConfigDTO;
import com.wasupstudio.model.entity.ParentConfiglEntity;
import com.wasupstudio.model.entity.StudentConfiglEntity;
import com.wasupstudio.service.AbstractService;
import com.wasupstudio.service.ParentConfigService;
import com.wasupstudio.service.StudentConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentConfigServiceImpl extends AbstractService<StudentConfiglEntity> implements StudentConfigService {

    @Autowired
    StudentConfigConverter studentConfigConverter;
    @Autowired
    StudentConfigMapper studentConfigMapper;
    @Override
    public void save(StudentConfigDTO dto) {
        this.save(studentConfigConverter.DTOtoItem(dto));
    }

    @Override
    public void deleteByScriptDetailId(Integer scriptDetailId) {
        studentConfigMapper.deleteByScriptDetailId(scriptDetailId);
    }

    @Override
    public void batchSave(List<StudentConfigDTO> list, Integer scriptDetailId) {
        List<StudentConfiglEntity> studentConfiglEntities = studentConfigConverter.DTOsToItems(list);
        studentConfigMapper.batchInsert(studentConfiglEntities, scriptDetailId);
    }

    @Override
    public StudentConfiglEntity findOne(Integer id) {
        return this.findById(id);
    }

    @Override
    public List<StudentConfiglEntity> findByScriptDetailId(Integer scriptDetailId) {
        return studentConfigMapper.findByScriptDetailId(scriptDetailId);
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
    public void update(StudentConfigDTO dto) {
        StudentConfiglEntity entity = studentConfigConverter.DTOtoItem(dto);
        update(entity);
    }
}
