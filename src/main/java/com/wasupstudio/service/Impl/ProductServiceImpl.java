package com.wasupstudio.service.Impl;

import com.wasupstudio.model.BasePageInfo;
import com.wasupstudio.model.entity.ProductEntity;
import com.wasupstudio.service.AbstractService;
import com.wasupstudio.service.ProductService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl extends AbstractService<ProductEntity> implements ProductService {

  @Override
  public ProductEntity findOne(Long id) {
    return this.findById(id);
  }

  @Override
  public List<ProductEntity> findByIds(List<String> productIds) {
    String ids = String.join(",", productIds);
    return this.findByIds(ids);
  }

  @Override
  public BasePageInfo findAllData() {
    return null;
  }
}
