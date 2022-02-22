package com.mcrmk.springboot.ecommerce.controller;

import com.mcrmk.springboot.ecommerce.annotation.AdminPermission;
import com.mcrmk.springboot.ecommerce.model.request.ProductRequest;
import com.mcrmk.springboot.ecommerce.model.response.ProductResponse;
import com.mcrmk.springboot.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @AdminPermission
    @PostMapping("")
    public ProductResponse create(@RequestBody ProductRequest productRequest){
        return service.create(productRequest);
    }

    @GetMapping("/{id}")
    public ProductResponse retrieve(@PathVariable String id) {
        return service.retrieve(id);
    }

    @GetMapping("")
    public List<ProductResponse> list(){
        return service.list();
    }

    @AdminPermission
    @PutMapping("/{id}")
    public ProductResponse update(@RequestBody ProductRequest productRequest, @PathVariable String id){
        return service.update(productRequest, id);
    }

    @AdminPermission
    @DeleteMapping("/{id}")
    public ProductResponse delete(@PathVariable String id){
        return service.delete(id);
    }

}
