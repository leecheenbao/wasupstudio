package com.wasupstudio.service;

import com.wasupstudio.model.BasePageInfo;
import com.wasupstudio.model.entity.ProductEntity;
import java.util.List;

public interface ProductService {
//  void save(ProductDTO productDTO);

  ProductEntity findOne(Long id);

  List<ProductEntity> findByIds(List<String> productIds);

  BasePageInfo findAllData();
//  void update(ProductDTO productDTO);
}
