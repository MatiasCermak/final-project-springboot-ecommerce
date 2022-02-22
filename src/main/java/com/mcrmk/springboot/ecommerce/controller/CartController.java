package com.mcrmk.springboot.ecommerce.controller;

import com.mcrmk.springboot.ecommerce.model.database.document.common.CartDetail;
import com.mcrmk.springboot.ecommerce.model.response.CartResponse;
import com.mcrmk.springboot.ecommerce.model.response.ProductResponse;
import com.mcrmk.springboot.ecommerce.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService service;


    @PostMapping("/add")
    public ProductResponse addProduct(@RequestBody CartDetail cartDetail){
        return service.addProduct(cartDetail);
    }

    @GetMapping("")
    public CartResponse list(){
        return service.list();
    }

    @PutMapping("/{id}")
    public CartResponse update(@PathVariable String id, @RequestBody Integer quantity){
        return service.update(id, quantity);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id){
        service.delete(id);
    }

    @GetMapping("/clear")
    public CartResponse clear(){
        return service.clear();
    }
}
