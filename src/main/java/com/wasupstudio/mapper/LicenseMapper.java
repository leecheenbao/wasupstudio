package com.wasupstudio.mapper;

import com.wasupstudio.model.entity.LicenseEntity;
import com.wasupstudio.util.CommonMapper;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface LicenseMapper extends CommonMapper<LicenseEntity> {
    @ResultMap("BaseResultMap")
    List<LicenseEntity> findByACTDate(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

    @ResultMap("BaseResultMap")
    @Select("SELECT * FROM wa_license WHERE CUSTOMER_EMAIL = #{customerMail} AND ACTIVATED = 1 AND expiration_date >= NOW()")
    List<LicenseEntity> findByCustomerEmail(@Param("customerMail") String customerMail);

    @ResultMap("BaseResultMap")
    @Select("SELECT * FROM wa_license WHERE license_key = #{licenseKey} AND ACTIVATED = #{activated}")
    List<LicenseEntity> findByLicenseAndActivated(@Param("licenseKey") String licenseKey, @Param("activated") Integer activated);

    @ResultMap("BaseResultMap")
    @Update("UPDATE wa_license SET activated=1, customer_name=#{customerName}," +
            "customer_email=#{customerMail} , create_date = #{createDate}WHERE license_key =#{licenseKey} AND activated = 0")
    boolean verifyLicense(@Param("licenseKey") String licenseKey,
                          @Param("customerMail") String customerMail,
                          @Param("customerName") String customerName,
                          @Param("createDate") Date createDate);

    @ResultMap("BaseResultMap")
    @Update("SELECT * FROM wa_license WHERE license_key =#{licenseKey}")
    LicenseEntity findByLicenseKey(@Param("licenseKey") String licenseKey);
}
