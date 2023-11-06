package com.wasupstudio.converter;

import com.wasupstudio.mapper.ProductMapper;
import com.wasupstudio.model.dto.ProductDTO;
import com.wasupstudio.model.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductConverter {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductEntity DTOtoItem(ProductDTO dto);

    List<ProductEntity> DTOsToItems(List<ProductDTO> dtos);

    ProductDTO ItemToDTO(ProductEntity entity);

    List<ProductDTO> ItemsToDTOs(List<ProductEntity> dto);
}