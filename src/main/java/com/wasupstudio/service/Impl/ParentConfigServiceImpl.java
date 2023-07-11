package com.wasupstudio.service.Impl;

import com.wasupstudio.model.BasePageInfo;
import com.wasupstudio.model.dto.ScriptDetailDTO;
import com.wasupstudio.model.entity.ParentConfiglEntity;
import com.wasupstudio.model.entity.ScriptDetailEntity;
import com.wasupstudio.service.AbstractService;
import com.wasupstudio.service.ParentConfigService;
import com.wasupstudio.service.ScriptDetailService;
import com.wasupstudio.util.ValueValidator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParentConfigServiceImpl extends AbstractService<ParentConfiglEntity> implements ParentConfigService {

    @Override
    public void save(ParentConfiglEntity entity) {
        this.save(entity);
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
