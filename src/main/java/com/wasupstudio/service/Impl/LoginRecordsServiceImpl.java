package com.wasupstudio.service.Impl;

import com.wasupstudio.mapper.LoginRecordsMapper;
import com.wasupstudio.model.BasePageInfo;
import com.wasupstudio.model.entity.LoginRecordsEntity;
import com.wasupstudio.service.AbstractService;
import com.wasupstudio.service.LoginRecordsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class LoginRecordsServiceImpl extends AbstractService<LoginRecordsEntity> implements LoginRecordsService {
    @Autowired
    public LoginRecordsMapper loginRecordsMapper;


    @Override
    public BasePageInfo findAllData() {
        List<LoginRecordsEntity> list = this.findAll();
        BasePageInfo basePageInfo = new BasePageInfo<>();
        basePageInfo.setList(list);
        basePageInfo.setTotal(list.size());
        return basePageInfo;
    }
}


