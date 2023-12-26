package com.wasupstudio.service;

import com.wasupstudio.model.BasePageInfo;
import com.wasupstudio.model.dto.ProductDTO;
import com.wasupstudio.model.entity.ProductEntity;
import java.util.List;

public interface ProductService {
  void save(ProductDTO productDTO);

  void delete(Long productId);

  ProductEntity findOne(Long productId);

  List<ProductEntity> findByIds(List<String> productIds);

  BasePageInfo findAllData();
  void update(ProductDTO productDTO);

  void subProduct(Long orderId, Integer quantity);
}
