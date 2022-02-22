package com.mcrmk.springboot.ecommerce.service;

import com.mcrmk.springboot.ecommerce.model.request.CategoryRequest;
import com.mcrmk.springboot.ecommerce.model.response.CategoryResponse;

import java.util.List;

public interface CategoryService {
    CategoryResponse create(CategoryRequest categoryRequest);
    CategoryResponse retrieve(String id);
    CategoryResponse retrieveByName(String name);
    List<CategoryResponse> list();
    CategoryResponse update(CategoryRequest categoryRequest, String id);
    CategoryResponse delete(String id);
}
