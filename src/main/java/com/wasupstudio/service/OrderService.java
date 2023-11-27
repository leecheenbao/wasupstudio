package com.wasupstudio.service;

import com.wasupstudio.model.BasePageInfo;
import com.wasupstudio.model.dto.OrderSearchDTO;
import com.wasupstudio.model.entity.OrderEntity;
import com.wasupstudio.model.query.OrderQuery;

public interface OrderService {

  void save(OrderEntity orderEntity);

  OrderQuery findOne(Long id);

  BasePageInfo findAllData();

  BasePageInfo findByCondiction(OrderSearchDTO orderSearchDTO);
  void updateData(OrderEntity orderEntity);
}
