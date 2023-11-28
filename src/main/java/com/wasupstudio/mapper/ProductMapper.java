package com.wasupstudio.mapper;

import com.wasupstudio.model.dto.ProductDTO;
import com.wasupstudio.model.entity.ProductEntity;
import com.wasupstudio.util.CommonMapper;
import org.apache.ibatis.annotations.Update;


public interface ProductMapper extends CommonMapper<ProductEntity> {

    @Update("UPDATE wa_products SET quantity = quantity - #{quantity} WHERE product_id = #{productId}")
    void subProduct(Long productId, Integer quantity);
}
