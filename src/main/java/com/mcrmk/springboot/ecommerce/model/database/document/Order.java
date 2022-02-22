package com.mcrmk.springboot.ecommerce.model.database.document;

import com.mcrmk.springboot.ecommerce.model.database.document.common.CartDetail;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document("ordenes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Order {
    @Id
    private String id;
    private Integer orderNumber;
    private String userId;
    private String userEmail;
    private Date createdAt;
    private String address;
    @Builder.Default
    private String status = "GENERATED";
    private List<CartDetail> orderDetails;

}
