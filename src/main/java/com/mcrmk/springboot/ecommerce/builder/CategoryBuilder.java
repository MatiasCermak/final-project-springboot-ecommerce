package com.mcrmk.springboot.ecommerce.builder;

import com.mcrmk.springboot.ecommerce.model.database.document.Category;
import com.mcrmk.springboot.ecommerce.model.request.CategoryRequest;
import com.mcrmk.springboot.ecommerce.model.response.CategoryResponse;

public class CategoryBuilder {

    public static Category requestToDocument(CategoryRequest request) {
        return Category.builder()
                .name(request.getName())
                .build();
    }

    public static CategoryResponse documentToResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
