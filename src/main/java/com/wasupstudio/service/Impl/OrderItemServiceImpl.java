package com.wasupstudio.service.Impl;

import com.wasupstudio.mapper.OrderItemMapper;
import com.wasupstudio.model.entity.OrderItemEntity;
import com.wasupstudio.service.AbstractService;
import com.wasupstudio.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemServiceImpl extends AbstractService<OrderItemEntity> implements OrderItemService {

    @Autowired
    private OrderItemMapper orderItemMapper;
    @Override
    public List<OrderItemEntity> getOrderItemList(Long productId) {
        return orderItemMapper.getOrderItemList(productId);
    }
}
