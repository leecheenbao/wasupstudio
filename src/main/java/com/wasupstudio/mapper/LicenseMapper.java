package com.wasupstudio.mapper;

import com.wasupstudio.model.entity.LicenseEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;

import java.util.Date;
import java.util.List;

@Mapper
public interface LicenseMapper extends CommonMapper<LicenseEntity> {
    @ResultMap("BaseResultMap")
    List<LicenseEntity> findByACTDate(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

}
