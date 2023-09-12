package com.wasupstudio.service.Impl;

import com.wasupstudio.converter.ScriptQuestionConverter;
import com.wasupstudio.mapper.ScriptQuestionMapper;
import com.wasupstudio.model.BasePageInfo;
import com.wasupstudio.model.dto.ScriptQuestionDTO;
import com.wasupstudio.model.entity.ScriptQuestionEntity;
import com.wasupstudio.service.AbstractService;
import com.wasupstudio.service.ScriptQuestionOptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScriptQuestionServiceImpl extends AbstractService<ScriptQuestionEntity> implements ScriptQuestionOptionService {
    @Autowired
    public ScriptQuestionMapper scriptQuestionMapper;

    @Autowired
    public ScriptQuestionConverter scriptQuestionConverter;

    @Override
    public void save(ScriptQuestionDTO dto) {
        if (dto.getQuestionId()==null){
            this.save(scriptQuestionConverter.DTOtoItem(dto));
        } else {
            this.update(scriptQuestionConverter.DTOtoItem(dto));
        }

    }

    @Override
    public BasePageInfo<ScriptQuestionEntity> findByTaskId(Integer id) {
        List<ScriptQuestionEntity> list = scriptQuestionMapper.findByTaskId(id);
        BasePageInfo basePageInfo = new BasePageInfo<>();
        basePageInfo.setList(list);
        basePageInfo.setTotal(list.size());
        return basePageInfo;
    }

    @Override
    public BasePageInfo<ScriptQuestionEntity> findAllData() {
        List<ScriptQuestionEntity> list = this.findAll();
        BasePageInfo basePageInfo = new BasePageInfo<>();
        basePageInfo.setList(list);
        basePageInfo.setTotal(list.size());
        return basePageInfo;
    }

    @Override
    public void update(ScriptQuestionDTO dto) {
        this.update(scriptQuestionConverter.DTOtoItem(dto));
    }
}
