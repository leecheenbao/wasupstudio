package com.wasupstudio.mapper;

import com.wasupstudio.model.entity.LicenseEntity;
import com.wasupstudio.util.CommonMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

@Mapper
public interface LicenseMapper extends CommonMapper<LicenseEntity> {
    @ResultMap("BaseResultMap")
    List<LicenseEntity> findByACTDate(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

    @ResultMap("BaseResultMap")
    @Select("SELECT * FROM wa_license WHERE CUSTOMER_EMAIL = #{customerMail} AND ACTIVATED = 1 AND expiration_date >= NOW()")
    List<LicenseEntity> findByCustomerEmail(@Param("customerMail") String customerMail);

}
