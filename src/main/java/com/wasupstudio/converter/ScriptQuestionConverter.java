package com.wasupstudio.converter;

import com.wasupstudio.mapper.ScriptQuestionMapper;
import com.wasupstudio.model.dto.ScriptQuestionDTO;
import com.wasupstudio.model.entity.ScriptQuestionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ScriptQuestionConverter {
    ScriptQuestionMapper INSTANCE = Mappers.getMapper(ScriptQuestionMapper.class);

    ScriptQuestionEntity DTOtoItem(ScriptQuestionDTO dto);

    List<ScriptQuestionEntity> DTOsToItems(List<ScriptQuestionDTO> dtos);

    ScriptQuestionDTO ItemToDTO(ScriptQuestionEntity entity);

    List<ScriptQuestionDTO> ItemsToDTOs(List<ScriptQuestionEntity> dto);
}