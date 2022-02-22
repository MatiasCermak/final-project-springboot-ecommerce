package com.mcrmk.springboot.ecommerce.service;

import com.mcrmk.springboot.ecommerce.model.request.OrderRequest;
import com.mcrmk.springboot.ecommerce.model.response.OrderResponse;

import java.util.List;

public interface OrderService {

    OrderResponse create(OrderRequest orderRequest);
    List<OrderResponse> list();

}
