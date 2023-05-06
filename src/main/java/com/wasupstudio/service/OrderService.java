package com.wasupstudio.service;

import com.wasupstudio.model.BasePageInfo;
import com.wasupstudio.model.dto.OrderDTO;
import com.wasupstudio.model.entity.OrderEntity;
import java.util.Date;

public interface OrderService {

  void save(OrderDTO orderDTO);

  OrderEntity findOne(Integer id);

  BasePageInfo findAllData();

  BasePageInfo findByACTDate(Date startTime, Date endTime);

  void update(OrderDTO licenseDTO);

}
