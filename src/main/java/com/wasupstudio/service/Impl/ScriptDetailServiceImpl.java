package com.wasupstudio.service.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wasupstudio.mapper.ScriptDetailMapper;
import com.wasupstudio.model.BasePageInfo;
import com.wasupstudio.model.dto.ScriptDetailDTO;
import com.wasupstudio.model.entity.ScriptDetailEntity;
import com.wasupstudio.model.entity.ScriptEntity;
import com.wasupstudio.service.AbstractService;
import com.wasupstudio.service.ScriptDetailService;
import com.wasupstudio.util.ValueValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ScriptDetailServiceImpl extends AbstractService<ScriptDetailEntity> implements ScriptDetailService {
    @Autowired
    ScriptDetailMapper mapper;
    @Override
    public ScriptDetailEntity save(ScriptDetailDTO dto) throws JsonProcessingException {
        ScriptDetailEntity scriptDetailEntity = new ScriptDetailEntity();
        ObjectMapper objectMapper = new ObjectMapper();
        String additionInfoJson = objectMapper.writeValueAsString(dto.getAdditionalInfo());
        scriptDetailEntity.setScriptId(dto.getScriptId());
        scriptDetailEntity.setAdvisoryTime(dto.getAdvisoryTime());
        scriptDetailEntity.setDescription(dto.getDescription());
        scriptDetailEntity.setTodayScript(dto.getTodayScript());
        scriptDetailEntity.setAdditionalInfo(additionInfoJson);
        scriptDetailEntity.setTeachingUrl(dto.getTeachingUrl());
        scriptDetailEntity.setPeriod(dto.getPeriod());
        save(scriptDetailEntity);

        return findOne(scriptDetailEntity.getScriptDetailId());
    }

    @Override
    public ScriptDetailEntity findOne(Integer id) {
        return this.findById(id);
    }

    @Override
    public ScriptDetailEntity findByPeriod(Integer scriptId, Integer period) {
        return mapper.findByVerificationCode(scriptId, period);
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

        try{
            ScriptDetailEntity scriptDetailEntity = this.findOne(dto.getScriptDetilId());
            if (scriptDetailEntity != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                String additionInfoJson = objectMapper.writeValueAsString(dto.getAdditionalInfo());

                scriptDetailEntity.setAdvisoryTime(dto.getAdvisoryTime());
                scriptDetailEntity.setDescription(dto.getDescription());
                scriptDetailEntity.setTodayScript(dto.getTodayScript());
                scriptDetailEntity.setAdditionalInfo(additionInfoJson);
                scriptDetailEntity.setTeachingUrl(dto.getTeachingUrl());
                scriptDetailEntity.setPeriod(dto.getPeriod());
            }
            update(scriptDetailEntity);
        }catch (Exception e){
            e.getStackTrace();
        }
    }
}
