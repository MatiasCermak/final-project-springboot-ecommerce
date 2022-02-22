package com.mcrmk.springboot.ecommerce.service;

import com.mcrmk.springboot.ecommerce.model.request.ProductRequest;
import com.mcrmk.springboot.ecommerce.model.response.ProductResponse;

import java.util.List;

public interface ProductService {
    ProductResponse create(ProductRequest productRequest);
    ProductResponse retrieve(String id);
    List<ProductResponse> list();
    List<ProductResponse> listAdmin();
    List<ProductResponse> listByCategory(String categoryName);
    ProductResponse update(ProductRequest productRequest, String id);
    ProductResponse updateStock(String id, Integer quantity);
    ProductResponse delete(String id);
}
