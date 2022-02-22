package com.mcrmk.springboot.ecommerce.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mcrmk.springboot.ecommerce.model.database.document.Category;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductResponse {
    private String id;
    private String title;
    private String description;
    private String imageUrl;
    private Long unitPrice;
    private CategoryResponse category;
    private Integer stock;
    private Boolean isActive;
}
