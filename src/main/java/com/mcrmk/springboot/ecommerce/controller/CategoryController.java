package com.mcrmk.springboot.ecommerce.controller;

import com.mcrmk.springboot.ecommerce.annotation.AdminPermission;
import com.mcrmk.springboot.ecommerce.model.request.CategoryRequest;
import com.mcrmk.springboot.ecommerce.model.response.CategoryResponse;
import com.mcrmk.springboot.ecommerce.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService service;

    @AdminPermission
    @PostMapping("")
    public CategoryResponse create(@RequestBody CategoryRequest request){
        return service.create(request);
    }

    @GetMapping("/{id}")
    public CategoryResponse retrieve(@PathVariable String id){
        return service.retrieve(id);
    }

    @GetMapping("")
    public List<CategoryResponse> list(){
        return service.list();
    }

    @AdminPermission
    @PutMapping("/{id}")
    public CategoryResponse update(@RequestBody CategoryRequest categoryRequest, @PathVariable String id) {
        return service.update(categoryRequest, id);
    }

    @AdminPermission
    @DeleteMapping("/{id}")
    public CategoryResponse delete(@PathVariable String id) {
        return service.delete(id);
    }

}
