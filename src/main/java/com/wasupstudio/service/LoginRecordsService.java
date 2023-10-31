package com.wasupstudio.service;

import com.wasupstudio.model.BasePageInfo;
import com.wasupstudio.model.entity.LoginRecordsEntity;

public interface LoginRecordsService {

    void save (LoginRecordsEntity loginRecordsEntity);

    void update(LoginRecordsEntity loginRecordsEntity);

    BasePageInfo findAllData();

    BasePageInfo queryLoginRecord(String from, String to);

    BasePageInfo query7DayLoginRecord();

}
