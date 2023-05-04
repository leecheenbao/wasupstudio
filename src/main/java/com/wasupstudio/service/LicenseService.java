package com.wasupstudio.service;

import com.wasupstudio.model.dto.LicenseDTO;
import com.wasupstudio.model.entity.LicenseEntity;
import com.wasupstudio.model.BasePageInfo;

import java.util.Date;

public interface LicenseService {

    void save (LicenseDTO licenseDTO);

    LicenseEntity findOne(Integer id);

    BasePageInfo findAllData();

    BasePageInfo findByACTDate(Date startTime, Date endTime);

    void update(LicenseDTO licenseDTO);
}
