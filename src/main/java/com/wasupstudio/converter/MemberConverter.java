package com.wasupstudio.converter;

import com.wasupstudio.mapper.MemberMapper;
import com.wasupstudio.model.dto.MemberDTO;
import com.wasupstudio.model.entity.MemberEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MemberConverter {
    MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);

    MemberEntity DTOtoItem(MemberDTO dto);

    List<MemberEntity> DTOsToItems(List<MemberDTO> dtos);

    MemberDTO ItemToDTO(MemberEntity entity);

    List<MemberDTO> ItemsToDTOs(List<MemberEntity> dto);
}