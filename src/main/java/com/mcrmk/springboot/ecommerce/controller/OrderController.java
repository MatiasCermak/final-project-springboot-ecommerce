package com.mcrmk.springboot.ecommerce.controller;

import com.mcrmk.springboot.ecommerce.model.request.OrderRequest;
import com.mcrmk.springboot.ecommerce.model.response.OrderResponse;
import com.mcrmk.springboot.ecommerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    @PostMapping("")
    public OrderResponse create(@RequestBody OrderRequest orderRequest){
        return service.create(orderRequest);
    }

    @GetMapping("")
    public List<OrderResponse> list(){
        return service.list();
    }

}
