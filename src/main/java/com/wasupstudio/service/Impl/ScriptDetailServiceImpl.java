package com.wasupstudio.service.Impl;

import com.wasupstudio.model.BasePageInfo;
import com.wasupstudio.model.dto.ScriptDetailDTO;
import com.wasupstudio.model.entity.ScriptDetailEntity;
import com.wasupstudio.service.AbstractService;
import com.wasupstudio.service.ScriptDetailService;
import com.wasupstudio.util.ValueValidator;

import java.util.List;

public class ScriptDetailServiceImpl extends AbstractService<ScriptDetailEntity> implements ScriptDetailService {

    @Override
    public void save(ScriptDetailDTO entity) {
        this.save(entity);
    }

    @Override
    public ScriptDetailEntity findOne(Integer id) {
        return this.findById(id);
    }


    @Override
    public BasePageInfo findAllData() {

        List<ScriptDetailEntity> list = this.findAll();
        BasePageInfo basePageInfo = new BasePageInfo<>();
        basePageInfo.setList(list);
        basePageInfo.setTotal(list.size());
        return basePageInfo;
    }

    @Override
    public void update(ScriptDetailDTO dto) {

        ScriptDetailEntity scriptDetailEntity = this.findOne(dto.getScriptDetilId());
        if (scriptDetailEntity != null) {
            if (!ValueValidator.isNullOrZero(dto.getAdvisoryTime())) {
                scriptDetailEntity.setAdvisoryTime(dto.getAdvisoryTime());
            }
            if (!ValueValidator.isNullOrEmpty(dto.getMethodDescription())) {
                scriptDetailEntity.setMethodDescription(dto.getMethodDescription());
            }
            if (!ValueValidator.isNullOrEmpty(dto.getTodayScript())) {
                scriptDetailEntity.setTodayScript(dto.getTodayScript());
            }

            if (!ValueValidator.isNullOrEmpty(dto.getAdditionalInfo())) {
                scriptDetailEntity.setAdditionalInfo(dto.getAdditionalInfo());
            }

            if (!ValueValidator.isNullOrEmpty(dto.getTeachingMaterials())) {
                scriptDetailEntity.setTeachingMaterials(dto.getTeachingMaterials());
            }

        }
    }
}
