package com.wasupstudio.converter;

import com.wasupstudio.model.dto.ScriptDTO;
import com.wasupstudio.model.entity.ScriptEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ScriptConverter {

    ScriptDTO map(ScriptEntity scriptEntity);
    List<ScriptDTO> map(List<ScriptEntity> scriptEntityList);



}