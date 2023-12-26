package com.wasupstudio.mapper;

import com.wasupstudio.model.entity.OrderItemEntity;
import com.wasupstudio.util.CommonMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface OrderItemMapper extends CommonMapper<OrderItemEntity> {

    @Select("SELECT * FROM wa_order_items WHERE product_id = #{productId}")
    List<OrderItemEntity> getOrderItemList(Long productId);
}
