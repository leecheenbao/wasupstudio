package com.wasupstudio.service;

import com.wasupstudio.model.BasePageInfo;
import com.wasupstudio.model.CashFlowData;
import com.wasupstudio.model.dto.CashFlowReturnDataDTO;
import com.wasupstudio.model.dto.OrderDTO;
import com.wasupstudio.model.dto.OrderSearchDTO;
import com.wasupstudio.model.entity.OrderEntity;
import com.wasupstudio.model.query.OrderQuery;

import java.util.List;

public interface OrderService {

  void save(OrderEntity orderEntity);

  OrderEntity findOne(Long id);

  List<OrderQuery> findOrderDetail(Long id);
  BasePageInfo findAllData();

  BasePageInfo findByCondiction(OrderSearchDTO orderSearchDTO);
  void updateData(OrderEntity orderEntity);

  CashFlowData creatOrder(OrderDTO orderDTO);

  Boolean orderCallBack(CashFlowReturnDataDTO cashFlowReturnDataDTO);
}
