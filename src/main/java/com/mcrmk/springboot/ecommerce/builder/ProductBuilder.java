package com.mcrmk.springboot.ecommerce.builder;

import com.mcrmk.springboot.ecommerce.model.database.document.Category;
import com.mcrmk.springboot.ecommerce.model.database.document.Product;
import com.mcrmk.springboot.ecommerce.model.request.CategoryRequest;
import com.mcrmk.springboot.ecommerce.model.request.ProductRequest;
import com.mcrmk.springboot.ecommerce.model.response.CategoryResponse;
import com.mcrmk.springboot.ecommerce.model.response.ProductResponse;
import com.mcrmk.springboot.ecommerce.repository.CategoryRepository;

public class ProductBuilder {

    public static Product requestToDocument(ProductRequest request) {
        return Product.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .imageUrl(request.getImageUrl())
                .unitPrice(request.getUnitPrice())
                .categoryId(request.getCategoryId())
                .stock(request.getStock())
                .active(request.getActive())
                .build();
    }

    public static ProductResponse documentToResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .title(product.getTitle())
                .description(product.getDescription())
                .imageUrl(product.getImageUrl())
                .unitPrice(product.getUnitPrice())
                .stock(product.getStock())
                .isActive(product.getActive())
                .build();
    }

}
