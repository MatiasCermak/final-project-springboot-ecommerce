package com.mcrmk.springboot.ecommerce.model.database.document;

import com.mcrmk.springboot.ecommerce.model.database.document.common.CartDetail;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document("carritos")
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Cart {
    @Id
    private String id;
    private String userId;
    private String email;
    @LastModifiedDate
    private Date dateModified;
    private List<CartDetail> cartDetails;



}
