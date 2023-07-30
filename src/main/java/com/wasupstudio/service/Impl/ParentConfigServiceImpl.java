package com.wasupstudio.service.Impl;

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
    ParentConfigMapper parentConfigMapper;
    @Override
    public void save(ParentConfiglEntity entity) {
        this.save(entity);
    }

    @Override
    public void batchSave(List<ParentConfiglDTO> list) {

//        parentConfigMapper.batchInsert(list);
    }

    @Override
    public ParentConfiglEntity findOne(Integer id) {
        return this.findById(id);
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
    public void update(ParentConfiglEntity entity) {
        this.save(entity);
    }
}
