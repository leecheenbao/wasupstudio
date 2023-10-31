package com.wasupstudio.service;

import com.wasupstudio.model.BasePageInfo;
import com.wasupstudio.model.entity.LoginRecordsEntity;

public interface LoginRecordsService {

    void save (LoginRecordsEntity loginRecordsEntity);

    BasePageInfo findAllData();

    void update(LoginRecordsEntity loginRecordsEntity);

}
