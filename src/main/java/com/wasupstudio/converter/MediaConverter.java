package com.wasupstudio.converter;

import com.wasupstudio.model.dto.MediaDTO;
import com.wasupstudio.model.entity.MediaEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MediaConverter {

    MediaDTO map(MediaEntity mediaEntity);
    List<MediaDTO> map(List<MediaEntity> mediaEntityList);



}