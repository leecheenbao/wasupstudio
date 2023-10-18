package com.wasupstudio.service.Impl;

import com.wasupstudio.enums.CommonStatusEnum;
import com.wasupstudio.mapper.LicenseMapper;
import com.wasupstudio.model.BasePageInfo;
import com.wasupstudio.model.dto.LicenseDTO;
import com.wasupstudio.model.entity.LicenseEntity;
import com.wasupstudio.service.AbstractService;
import com.wasupstudio.service.LicenseService;
import com.wasupstudio.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class LicenseServiceImpl extends AbstractService<LicenseEntity> implements LicenseService {

    @Autowired
    public LicenseMapper licenseMapper;

    @Override
    public void save(LicenseDTO licenseDTO) {
        Date today = new Date();

        LicenseEntity licenseEntity = new LicenseEntity();
        licenseEntity.setLicenseKey(generate());
        licenseEntity.setActivated(CommonStatusEnum.DISABLE.getType());
        licenseEntity.setActivationDate(today);
        licenseEntity.setExpirationDate(DateUtils.addDays(today, 7));
        licenseEntity.setCustomerEmail(licenseDTO.getCustomerEmail());
        licenseEntity.setCustomerName(licenseDTO.getCustomerName());
        licenseEntity.setGenerate(licenseDTO.getGenerate());
        save(licenseEntity);
    }

    public static String generate() {
        LocalDate now = LocalDate.now();
        String dateString = now.format(DateTimeFormatter.ofPattern("MMdd"));
        UUID uuid = UUID.randomUUID();
        String str = uuid.toString().replace("-", "");
        String[] parts = {dateString, str.substring(0, 3), str.substring(3, 6), str.substring(6, 9), str.substring(9, 12)};
        return String.join("-", parts);
    }


    @Override
    public LicenseEntity findOne(Integer id) {
        return this.findById(id);
    }

    @Override
    public BasePageInfo findAllData() {
        List<LicenseEntity> list = this.findAll();
        BasePageInfo basePageInfo = new BasePageInfo<>();
        basePageInfo.setList(list);
        basePageInfo.setTotal(list.size());
        return basePageInfo;
    }

    @Override
    public BasePageInfo findByACTDate(Date startTime, Date endTime) {
        List<LicenseEntity> list = licenseMapper.findByACTDate(startTime, endTime);
        BasePageInfo basePageInfo = new BasePageInfo<>();
        basePageInfo.setList(list);
        basePageInfo.setTotal(list.size());

        return basePageInfo;
    }

    @Override
    public void update(LicenseDTO licenseDTO) {
        LicenseEntity license = this.findOne(licenseDTO.getId());
        if (license != null) {
            license.setActivated(licenseDTO.getActivated());
            license.setCustomerName(licenseDTO.getCustomerName());
            license.setCustomerEmail(licenseDTO.getCustomerEmail());
            license.setActivated(licenseDTO.getActivated());
            license.setGenerate(licenseDTO.getGenerate());
            this.update(license);
        }

    }

    @Override
    public List<LicenseEntity> findByEmailAndActivated(String email) {

        return licenseMapper.findByCustomerEmail(email);
    }
}
