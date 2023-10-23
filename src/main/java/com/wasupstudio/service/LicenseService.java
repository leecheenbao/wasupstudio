package com.wasupstudio.service;

import com.wasupstudio.model.BasePageInfo;
import com.wasupstudio.model.dto.LicenseDTO;
import com.wasupstudio.model.entity.LicenseEntity;

import java.util.Date;
import java.util.List;

public interface LicenseService {

    void save (LicenseDTO licenseDTO);

    boolean verify(LicenseDTO licenseDTO);

    LicenseEntity findOne(Integer id);

    BasePageInfo findAllData();

    BasePageInfo findByACTDate(Date startTime, Date endTime);

    void update(LicenseDTO licenseDTO);

    List<LicenseEntity> findByEmailAndActivated(String mail);

    List<LicenseEntity> findByLicenseKeyAndActivated(String licenseKey, Integer activated);
}
