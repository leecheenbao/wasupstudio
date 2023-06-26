package com.wasupstudio.service;

import com.wasupstudio.model.entity.OrderItemEntity;
import java.util.List;

public interface OrderItemService {

//  void save(OrderItemDTO orderItemDTO);
//
  void save(List<OrderItemEntity> orderItemEntities);
}
