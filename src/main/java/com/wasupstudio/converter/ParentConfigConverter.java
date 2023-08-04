package com.wasupstudio.converter;

import com.wasupstudio.mapper.MemberMapper;
import com.wasupstudio.mapper.ParentConfigMapper;
import com.wasupstudio.model.dto.MemberDTO;
import com.wasupstudio.model.dto.ParentConfiglDTO;
import com.wasupstudio.model.entity.MemberEntity;
import com.wasupstudio.model.entity.ParentConfiglEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ParentConfigConverter {
    ParentConfigMapper INSTANCE = Mappers.getMapper(ParentConfigMapper.class);

    ParentConfiglEntity DTOtoItem(ParentConfiglDTO dto);

    List<ParentConfiglEntity> DTOsToItems(List<ParentConfiglDTO> dtos);

    ParentConfiglDTO ItemToDTO(ParentConfiglEntity entity);

    List<ParentConfiglDTO> ItemsToDTOs(List<ParentConfiglEntity> dto);
}