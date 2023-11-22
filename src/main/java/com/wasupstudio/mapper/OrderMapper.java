package com.wasupstudio.mapper;

import com.wasupstudio.model.entity.OrderEntity;
import com.wasupstudio.model.query.OrderQuery;
import com.wasupstudio.util.CommonMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface OrderMapper extends CommonMapper<OrderEntity> {

    OrderQuery getOrderDetail(Long orderId);

}
