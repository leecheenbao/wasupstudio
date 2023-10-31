package com.wasupstudio.service.Impl;

import com.wasupstudio.mapper.LoginRecordsMapper;
import com.wasupstudio.model.BasePageInfo;
import com.wasupstudio.model.entity.LoginRecordsEntity;
import com.wasupstudio.model.query.LoginRecordsQuery;
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

    @Override
    public BasePageInfo queryLoginRecord(String from, String to) {
        String startTime = from + " 00:00:00";
        String endTime = to + " 23:59:59";

        List<LoginRecordsQuery> list = loginRecordsMapper.queryBDay(startTime, endTime);
        BasePageInfo basePageInfo = new BasePageInfo<>();
        basePageInfo.setList(list);
        basePageInfo.setTotal(list.size());
        return basePageInfo;
    }

    @Override
    public BasePageInfo query7DayLoginRecord() {
        List<LoginRecordsQuery> list = loginRecordsMapper.queryBy7Day();
        BasePageInfo basePageInfo = new BasePageInfo<>();
        basePageInfo.setList(list);
        basePageInfo.setTotal(list.size());
        return basePageInfo;
    }
}


