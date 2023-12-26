package com.wasupstudio.service.Impl;

import com.wasupstudio.constant.ProjectConstant;
import com.wasupstudio.converter.ProductConverter;
import com.wasupstudio.exception.BussinessException;
import com.wasupstudio.mapper.ProductMapper;
import com.wasupstudio.model.BasePageInfo;
import com.wasupstudio.model.dto.ProductDTO;
import com.wasupstudio.model.entity.OrderItemEntity;
import com.wasupstudio.model.entity.ProductEntity;
import com.wasupstudio.service.AbstractService;
import com.wasupstudio.service.OrderItemService;
import com.wasupstudio.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl extends AbstractService<ProductEntity> implements ProductService {

    @Autowired
    ProductMapper productMapper;

    @Autowired
    ProductConverter productConverter;

    @Autowired
    OrderItemService orderItemService;

    @Override
    public void save(ProductDTO productDTO) {
        if (productDTO.getQuantity() == null){
            productDTO.setQuantity(ProjectConstant.ProductDefaultQuantity.DEFAULT);
        }
        ProductEntity product = productConverter.DTOtoItem(productDTO);
        save(product);
    }

    @Override
    public void delete(Long productId) {
        List<OrderItemEntity> orderItemList = orderItemService.getOrderItemList(productId);
        if (orderItemList.size() != 0) {
            throw new BussinessException("無法刪除");
        }
        deleteById(Math.toIntExact(productId));
    }

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
        List<ProductEntity> products = findAll();
        BasePageInfo basePageInfo = new BasePageInfo<>();
        basePageInfo.setList(products);
        basePageInfo.setTotal(products.size());
        return basePageInfo;
    }

    @Override
    public void update(ProductDTO productDTO) {
        ProductEntity product = productConverter.DTOtoItem(productDTO);
        update(product);
    }

    @Override
    public void subProduct(Long product, Integer quantity) {
        productMapper.subProduct(product, quantity);
    }
}
