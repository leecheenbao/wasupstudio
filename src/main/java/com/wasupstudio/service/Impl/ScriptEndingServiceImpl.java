package com.wasupstudio.service.Impl;

import com.wasupstudio.converter.ScriptEndingConverter;
import com.wasupstudio.mapper.ScriptEndingMapper;
import com.wasupstudio.model.BasePageInfo;
import com.wasupstudio.model.dto.ScriptEndingDTO;
import com.wasupstudio.model.entity.ScriptEndingEntity;
import com.wasupstudio.service.AbstractService;
import com.wasupstudio.service.ScriptEndingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ScriptEndingServiceImpl extends AbstractService<ScriptEndingEntity> implements ScriptEndingService {
    @Autowired
    private ScriptEndingMapper scriptEndingMapper;

    @Autowired
    private ScriptEndingConverter scriptEndingConverter;

    @Override
    public void save(ScriptEndingDTO scriptEndingDTO) {
        ScriptEndingDTO dto = this.findOne(scriptEndingDTO.getScriptId());
        if (dto != null) {
            this.update(scriptEndingDTO);
        } else {
            this.save(scriptEndingConverter.DTOtoItem(scriptEndingDTO));
        }
    }

    @Override
    public ScriptEndingDTO findOne(Integer id) {
        return scriptEndingConverter.ItemToDTO(this.findById(id));
    }

    @Override
    public BasePageInfo findAllData() {
        List<ScriptEndingDTO> list = scriptEndingConverter.ItemsToDTOs(this.findAll());
        BasePageInfo basePageInfo = new BasePageInfo<>();
        basePageInfo.setList(list);
        basePageInfo.setTotal(list.size());
        return basePageInfo;
    }

    @Override
    public void update(ScriptEndingDTO scriptEndingDTO) {
        ScriptEndingDTO dto = this.findOne(scriptEndingDTO.getScriptId());
        if (dto != null) {
            ScriptEndingEntity scriptEndingEntity = scriptEndingConverter.DTOtoItem(dto);
            scriptEndingEntity.setEndingDescription(scriptEndingDTO.getEndingDescription());
            scriptEndingEntity.setAdvisoryTime(scriptEndingDTO.getAdvisoryTime());
            scriptEndingEntity.setEndingOne(scriptEndingDTO.getEndingOne());
            scriptEndingEntity.setEndingTwo(scriptEndingDTO.getEndingTwo());
            scriptEndingEntity.setEndingThree(scriptEndingDTO.getEndingThree());
            scriptEndingEntity.setEndingFour(scriptEndingDTO.getEndingFour());
            scriptEndingEntity.setOrderlyOne(scriptEndingDTO.getOrderlyOne());
            scriptEndingEntity.setOrderlyTwo(scriptEndingDTO.getOrderlyTwo());
            scriptEndingEntity.setOrderlyThree(scriptEndingDTO.getOrderlyThree());
            scriptEndingEntity.setOrderlyFour(scriptEndingDTO.getOrderlyFour());

            scriptEndingEntity.setRelationOne(scriptEndingDTO.getRelationOne());
            scriptEndingEntity.setRelationTwo(scriptEndingDTO.getRelationTwo());
            scriptEndingEntity.setRelationThree(scriptEndingDTO.getRelationThree());
            scriptEndingEntity.setRelationFour(scriptEndingDTO.getRelationFour());
            update(scriptEndingEntity);
        }
    }
}
