package com.wasupstudio.service.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.wasupstudio.mapper.ScriptMapper;
import com.wasupstudio.model.BasePageInfo;
import com.wasupstudio.model.dto.ScriptDTO;
import com.wasupstudio.model.entity.ScriptEntity;
import com.wasupstudio.model.query.ScriptQuery;
import com.wasupstudio.service.AbstractService;
import com.wasupstudio.service.ScriptService;
import com.wasupstudio.util.ValueValidator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

        List<ScriptEntity> scriptEntityList = this.findAll();
        List<ScriptQuery> list = new ArrayList<>();
        for (ScriptEntity scriptEntity : scriptEntityList){
            ScriptQuery scriptQuery = getScriptQuery(scriptEntity);
            list.add(scriptQuery);
        }
        BasePageInfo basePageInfo = new BasePageInfo<>();
        basePageInfo.setList(list);
        basePageInfo.setTotal(list.size());
        return basePageInfo;
    }

    @Override
    public void update(ScriptDTO scriptDTO) {
        ScriptEntity scriptEntity = this.findOne(scriptDTO.getScriptId());
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

        this.update(scriptEntity);

    }


    public static ScriptQuery getScriptQuery(ScriptEntity scriptEntity) {
        Gson gson = new Gson();
        List<String> tips = gson.fromJson(scriptEntity.getTips(), List.class);
        List<String> goals = gson.fromJson(scriptEntity.getGoal(), List.class);
        List<String> preambles = gson.fromJson(scriptEntity.getPreamble(), List.class);
        ScriptQuery scriptQuery = new ScriptQuery();
        BeanUtils.copyProperties(scriptEntity, scriptQuery);
        scriptQuery.setTips(tips);
        scriptQuery.setGoal(goals);
        scriptQuery.setPreamble(preambles);
        return scriptQuery;
    }
}
