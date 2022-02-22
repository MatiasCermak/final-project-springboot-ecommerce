package com.mcrmk.springboot.ecommerce.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mcrmk.springboot.ecommerce.model.database.document.common.CartDetail;
import lombok.*;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CartResponse {
    private String userId;
    private String email;
    private Date dateModified;
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private List<CartDetailResponse> cartDetails;
    @Builder.Default
    private Boolean missingItems = false;
}
