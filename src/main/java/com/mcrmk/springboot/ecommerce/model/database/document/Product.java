package com.mcrmk.springboot.ecommerce.model.database.document;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("productos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Product {
    @Id
    private String id;
    private String title;
    private String description;
    private String imageUrl;
    private Long unitPrice;
    private String categoryId;
    private Integer stock;
    @Builder.Default
    private Boolean active = true;


}
