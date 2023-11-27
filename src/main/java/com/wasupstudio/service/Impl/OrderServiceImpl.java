package com.wasupstudio.service.Impl;

import com.wasupstudio.mapper.OrderMapper;
import com.wasupstudio.model.BasePageInfo;
import com.wasupstudio.model.dto.OrderSearchDTO;
import com.wasupstudio.model.entity.OrderEntity;
import com.wasupstudio.model.query.OrderQuery;
import com.wasupstudio.service.AbstractService;
import com.wasupstudio.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    List<OrderEntity> list = orderMapper.selectAll();
    BasePageInfo basePageInfo = new BasePageInfo<>();
    basePageInfo.setList(list);
    basePageInfo.setTotal(list.size());
    return basePageInfo;
  }

  @Override
  public BasePageInfo findByCondiction(OrderSearchDTO orderSearchDTO) {
    String orderId = orderSearchDTO.getOrderId().toUpperCase();
    orderId = orderId.replace("SW_","");
    orderSearchDTO.setOrderId(orderId);
    List<OrderEntity> list = orderMapper.findByCondiction(orderSearchDTO);
    BasePageInfo basePageInfo = new BasePageInfo<>();
    basePageInfo.setList(list);
    basePageInfo.setTotal(list.size());
    return basePageInfo;
  }

  @Override
  public void updateData(OrderEntity orderEntity) {
    this.update(orderEntity);
  }
}
