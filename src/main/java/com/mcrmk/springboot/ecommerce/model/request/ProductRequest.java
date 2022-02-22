package com.mcrmk.springboot.ecommerce.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mcrmk.springboot.ecommerce.model.database.document.Category;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private String title;
    private String description;
    private String imageUrl;
    private Long unitPrice;
    private String categoryId;
    @Builder.Default
    private Integer stock = 0;
    @Builder.Default
    private Boolean active = true;
}
