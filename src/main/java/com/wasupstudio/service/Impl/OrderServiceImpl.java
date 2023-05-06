package com.wasupstudio.service.Impl;

import com.wasupstudio.model.BasePageInfo;
import com.wasupstudio.model.dto.OrderDTO;
import com.wasupstudio.model.entity.OrderEntity;
import com.wasupstudio.service.AbstractService;
import com.wasupstudio.service.OrderService;
import java.util.Date;
import org.springframework.stereotype.Service;

//@Service
public class OrderServiceImpl extends AbstractService<OrderEntity> implements OrderService {

  @Override
  public void save(OrderDTO orderDTO) {

  }

  @Override
  public OrderEntity findOne(Integer id) {
    return null;
  }

  @Override
  public BasePageInfo findAllData() {
    return null;
  }

  @Override
  public BasePageInfo findByACTDate(Date startTime, Date endTime) {
    return null;
  }

  @Override
  public void update(OrderDTO licenseDTO) {

  }
}
