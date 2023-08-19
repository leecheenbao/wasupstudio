package com.wasupstudio.converter;

import com.wasupstudio.mapper.ScriptEndingMapper;
import com.wasupstudio.model.dto.ScriptEndingDTO;
import com.wasupstudio.model.entity.ScriptEndingEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ScriptEndingConverter {
    ScriptEndingMapper INSTANCE = Mappers.getMapper(ScriptEndingMapper.class);

    ScriptEndingEntity DTOtoItem(ScriptEndingDTO dto);

    List<ScriptEndingEntity> DTOsToItems(List<ScriptEndingDTO> dtos);

    ScriptEndingDTO ItemToDTO(ScriptEndingEntity entity);

    List<ScriptEndingDTO> ItemsToDTOs(List<ScriptEndingEntity> dto);
}