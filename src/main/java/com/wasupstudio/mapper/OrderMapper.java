package com.wasupstudio.mapper;

import com.wasupstudio.model.entity.OrderEntity;
import com.wasupstudio.util.CommonMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper extends CommonMapper<OrderEntity> {

}
