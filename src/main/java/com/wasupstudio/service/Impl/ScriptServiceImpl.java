package com.wasupstudio.service.Impl;

import com.wasupstudio.mapper.ScriptMapper;
import com.wasupstudio.model.BasePageInfo;
import com.wasupstudio.model.dto.ScriptDTO;
import com.wasupstudio.model.entity.LicenseEntity;
import com.wasupstudio.model.entity.ScriptEntity;
import com.wasupstudio.service.AbstractService;
import com.wasupstudio.service.ScriptService;
import com.wasupstudio.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ScriptServiceImpl extends AbstractService<ScriptEntity> implements ScriptService {
    @Autowired
    public ScriptMapper scriptMapper;

    @Override
    public void save(ScriptDTO scriptDTO) {

        ScriptEntity scriptEntity = new ScriptEntity();
        scriptEntity.setScriptId(scriptDTO.getScriptId());
        scriptEntity.setTitle(scriptDTO.getTitle());
        scriptEntity.setAuthor(scriptDTO.getAuthor());
        scriptEntity.setDescription(scriptDTO.getDescription());
        scriptEntity.setStatus(scriptDTO.getStatus());
        scriptEntity.setCreateTime(new Date());
        scriptEntity.setPeriod(scriptDTO.getPeriod());
        scriptEntity.setUpdateTime(new Date());
        save(scriptEntity);
    }

    @Override
    public ScriptEntity findOne(Integer id) {
        return this.findById(id);
    }

    @Override
    public ScriptEntity getScriptByTitle(String title) {

        return null;
    }

    @Override
    public BasePageInfo findAllData() {

        List<ScriptEntity> list = this.findAll();
        BasePageInfo basePageInfo = new BasePageInfo<>();
        basePageInfo.setList(list);
        basePageInfo.setTotal(list.size());
        return basePageInfo;
    }

    @Override
    public void update(ScriptDTO scriptDTO) {
        ScriptEntity scriptEntity = this.findOne(scriptDTO.getScriptId());
        if (scriptEntity != null){

            scriptEntity.setScriptId(scriptDTO.getScriptId());
            scriptEntity.setTitle(scriptDTO.getTitle());
            scriptEntity.setAuthor(scriptDTO.getAuthor());
            scriptEntity.setDescription(scriptDTO.getDescription());
            scriptEntity.setStatus(scriptDTO.getStatus());
            scriptEntity.setPeriod(scriptDTO.getPeriod());
            scriptEntity.setUpdateTime(new Date());

            this.update(scriptEntity);
        }
    }
}
