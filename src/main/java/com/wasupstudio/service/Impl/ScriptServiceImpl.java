package com.wasupstudio.service.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wasupstudio.mapper.ScriptMapper;
import com.wasupstudio.model.BasePageInfo;
import com.wasupstudio.model.dto.ScriptDTO;
import com.wasupstudio.model.entity.ScriptEntity;
import com.wasupstudio.service.AbstractService;
import com.wasupstudio.service.ScriptService;
import com.wasupstudio.util.ValueValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ScriptServiceImpl extends AbstractService<ScriptEntity> implements ScriptService {
    @Autowired
    public ScriptMapper scriptMapper;

    @Override
    public ScriptEntity save(ScriptDTO scriptDTO) {
        ScriptEntity scriptEntity = new ScriptEntity();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String goalJson = objectMapper.writeValueAsString(scriptDTO.getGoal());
            String tipsJson = objectMapper.writeValueAsString(scriptDTO.getTips());
            String preambleJson = objectMapper.writeValueAsString(scriptDTO.getPreamble());

            scriptEntity.setScriptId(scriptDTO.getScriptId());
            scriptEntity.setTitle(scriptDTO.getTitle());
            scriptEntity.setAuthor(scriptDTO.getAuthor());
            scriptEntity.setDescription(scriptDTO.getDescription());
            scriptEntity.setStatus(scriptDTO.getStatus());
            scriptEntity.setCreateTime(new Date());
            scriptEntity.setScriptPeriod(scriptDTO.getScriptPeriod());
            scriptEntity.setUpdateTime(new Date());
            scriptEntity.setGoal(goalJson);
            scriptEntity.setTips(tipsJson);
            scriptEntity.setPreamble(preambleJson);

        } catch (JsonProcessingException e) {
            // 转换为 JSON 字符串时出错
            e.printStackTrace();
        }

        save(scriptEntity);
        ScriptEntity savedScriptEntity = findOne(scriptEntity.getScriptId());

        return savedScriptEntity;
    }

    @Override
    public ScriptEntity findOne(Integer id) {
        return this.findById(id);
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
            if (!ValueValidator.isNullOrEmpty(scriptDTO.getTitle())){
                scriptEntity.setTitle(scriptDTO.getTitle());
            }
            if (!ValueValidator.isNullOrEmpty(scriptDTO.getDescription())){
                scriptEntity.setDescription(scriptDTO.getDescription());
            }
            if (!ValueValidator.isNullOrZero(scriptDTO.getScriptPeriod())){
                scriptEntity.setScriptPeriod(scriptDTO.getScriptPeriod());
            }
//            if (!ValueValidator.isNullOrEmpty(scriptDTO.getTips())) {
//                scriptEntity.setTips(scriptDTO.getTips());
//            }
//            if (!ValueValidator.isNullOrEmpty(scriptDTO.getGoal())) {
//                scriptEntity.setGoal(scriptDTO.getGoal());
//            }

            scriptEntity.setAuthor(scriptDTO.getAuthor());
            scriptEntity.setStatus(scriptDTO.getStatus());
            scriptEntity.setUpdateTime(new Date());

            this.update(scriptEntity);
        }
    }


}
