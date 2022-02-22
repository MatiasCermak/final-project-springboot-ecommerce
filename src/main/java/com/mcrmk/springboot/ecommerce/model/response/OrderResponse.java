package com.mcrmk.springboot.ecommerce.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderResponse {
    private String id;
    private String userId;
    private Integer orderNumber;
    private String userEmail;
    private Date createdAt;
    private String address;
    private List<CartDetailResponse> orderDetails;
}
