package com.wasupstudio.service.Impl;

import com.wasupstudio.converter.ParentConfigConverter;
import com.wasupstudio.mapper.ParentConfigMapper;
import com.wasupstudio.model.BasePageInfo;
import com.wasupstudio.model.dto.ParentConfiglDTO;
import com.wasupstudio.model.entity.ParentConfiglEntity;
import com.wasupstudio.service.AbstractService;
import com.wasupstudio.service.ParentConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParentConfigServiceImpl extends AbstractService<ParentConfiglEntity> implements ParentConfigService {
    @Autowired
    ParentConfigConverter parentConfigConverter;
    @Autowired
    ParentConfigMapper parentConfigMapper;
    @Override
    public void save(ParentConfiglDTO dto) {
        this.save(parentConfigConverter.DTOtoItem(dto));
    }

    @Override
    public void batchSave(List<ParentConfiglDTO> list, Integer scriptDetailId) {
        List<ParentConfiglEntity> parentConfiglEntities = parentConfigConverter.DTOsToItems(list);
        parentConfigMapper.batchInsert(parentConfiglEntities, scriptDetailId);
    }

    @Override
    public void batchUpdate(List<ParentConfiglDTO> list, Integer scriptDetailId) {
        List<ParentConfiglEntity> parentConfiglEntities = parentConfigConverter.DTOsToItems(list);
        parentConfigMapper.batchInsert(parentConfiglEntities, scriptDetailId);
    }

    @Override
    public ParentConfiglEntity findOne(Integer id) {
        return this.findById(id);
    }

    @Override
    public List<ParentConfiglEntity> findByScriptDetailId(Integer scriptDetailId) {
        return parentConfigMapper.findByScriptDetailId(scriptDetailId);
    }


    @Override
    public BasePageInfo findAllData() {

        List<ParentConfiglEntity> list = this.findAll();
        BasePageInfo basePageInfo = new BasePageInfo<>();
        basePageInfo.setList(list);
        basePageInfo.setTotal(list.size());
        return basePageInfo;
    }

    @Override
    public void update(ParentConfiglDTO dto) {
        ParentConfiglEntity entity = parentConfigConverter.DTOtoItem(dto);
        update(entity);
    }
}
