package com.mcrmk.springboot.ecommerce.builder;

import com.mcrmk.springboot.ecommerce.model.database.document.Order;
import com.mcrmk.springboot.ecommerce.model.response.OrderResponse;
public class OrderBuilder {

    public static OrderResponse documentToResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .address(order.getAddress())
                .userId(order.getUserId())
                .userEmail(order.getUserEmail())
                .createdAt(order.getCreatedAt())
                .orderNumber(order.getOrderNumber())
                .build();
    }

}
