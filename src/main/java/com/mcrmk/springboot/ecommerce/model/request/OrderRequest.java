package com.mcrmk.springboot.ecommerce.model.request;

import com.mcrmk.springboot.ecommerce.model.database.document.common.CartDetail;
import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {

    private String address;
    private List<CartDetail> orderDetails;
    @Builder.Default
    private Boolean errorOnLowStock = true;
    @Builder.Default
    private Boolean errorOnMissingItems = true;

}
