package com.wasupstudio.service.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wasupstudio.converter.ParentConfigConverter;
import com.wasupstudio.converter.StudentConfigConverter;
import com.wasupstudio.mapper.ScriptDetailMapper;
import com.wasupstudio.model.BasePageInfo;
import com.wasupstudio.model.dto.ParentConfiglDTO;
import com.wasupstudio.model.dto.ScriptDetailDTO;
import com.wasupstudio.model.entity.ParentConfiglEntity;
import com.wasupstudio.model.entity.ScriptDetailEntity;
import com.wasupstudio.model.entity.StudentConfiglEntity;
import com.wasupstudio.service.AbstractService;
import com.wasupstudio.service.ParentConfigService;
import com.wasupstudio.service.ScriptDetailService;
import com.wasupstudio.service.StudentConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class ScriptDetailServiceImpl extends AbstractService<ScriptDetailEntity> implements ScriptDetailService {
    @Autowired
    ScriptDetailMapper mapper;

    @Autowired
    ParentConfigService parentConfigService;
    @Autowired
    StudentConfigService studentConfigService;

    @Autowired
    ParentConfigConverter parentConfigConverter;
    @Autowired
    StudentConfigConverter studentConfigConverter;

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
    public List<ScriptDetailDTO> findByScriptId(Integer scriptId) {
        List<ScriptDetailEntity> list = mapper.findByScriptId(scriptId);
        List<ScriptDetailDTO> dtos = new ArrayList<>();
        for (ScriptDetailEntity entity : list){
            ScriptDetailDTO dto = new ScriptDetailDTO();
            dto.setScriptId(entity.getScriptId());
            dto.setScriptDetilId(entity.getScriptDetailId());
            dto.setPeriod(entity.getPeriod());
            dto.setDescription(entity.getDescription());
            dto.setTodayScript(entity.getTodayScript());
            dto.setAdvisoryTime(entity.getAdvisoryTime());
            dto.setTeachingUrl(entity.getTeachingUrl());

            Gson gson = new Gson();
            List<String> additionalInfo = gson.fromJson(entity.getAdditionalInfo(), new TypeToken<List<String>>() {}.getType());
            dto.setAdditionalInfo(additionalInfo);
            dtos.add(dto);

            List<ParentConfiglEntity> parentConfigs = parentConfigService.findByScriptDetailId(entity.getScriptDetailId());
            dto.setParentConfigs(parentConfigConverter.ItemsToDTOs(parentConfigs));

            List<StudentConfiglEntity> studentConfigs = studentConfigService.findByScriptDetailId(entity.getScriptDetailId());
            dto.setStudentConfigs(studentConfigConverter.ItemsToDTOs(studentConfigs));
        }
        return dtos;
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
