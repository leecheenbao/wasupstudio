package com.wasupstudio.service.Impl;

import com.wasupstudio.mapper.OrderMapper;
import com.wasupstudio.model.BasePageInfo;
import com.wasupstudio.model.entity.OrderEntity;
import com.wasupstudio.model.query.OrderQuery;
import com.wasupstudio.service.AbstractService;
import com.wasupstudio.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl extends AbstractService<OrderEntity> implements OrderService {

  @Autowired
  private OrderMapper orderMapper;
  @Override
  public OrderQuery findOne(Long orderId) {
    OrderQuery order = orderMapper.getOrderDetail(orderId);
    return order;
  }

  @Override
  public BasePageInfo findAllData() {
    return null;
  }

  @Override
  public void updateData(OrderEntity orderEntity) {
    this.update(orderEntity);
  }
}
