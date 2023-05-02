package com.wasupstudio.converter;

import com.wasupstudio.mapper.LicenseMapper;
import com.wasupstudio.model.entity.LicenseEntity;
import com.wasupstudio.model.vo.LicenseVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface LicenseConverter {
    LicenseMapper INSTANCE = Mappers.getMapper(LicenseMapper.class);

    LicenseEntity map(LicenseVo licenseVo);

    List<LicenseEntity> map(List<LicenseVo> licenseVoList);
}