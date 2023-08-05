package com.wasupstudio.converter;

import com.wasupstudio.mapper.StudentConfigMapper;
import com.wasupstudio.model.dto.ScriptDetailDTO;
import com.wasupstudio.model.dto.StudentConfigDTO;
import com.wasupstudio.model.entity.StudentConfiglEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StudentConfigConverter {
    StudentConfigMapper INSTANCE = Mappers.getMapper(StudentConfigMapper.class);

    StudentConfiglEntity DTOtoItem(StudentConfigDTO dto);

    List<StudentConfiglEntity> DTOsToItems(List<StudentConfigDTO> dtos);

    StudentConfigDTO ItemToDTO(StudentConfiglEntity entity);

    List<StudentConfigDTO> ItemsToDTOs(List<StudentConfiglEntity> dto);
}