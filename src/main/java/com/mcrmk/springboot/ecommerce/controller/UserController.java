package com.mcrmk.springboot.ecommerce.controller;

import com.mcrmk.springboot.ecommerce.model.request.UserRequest;
import com.mcrmk.springboot.ecommerce.model.response.UserResponse;
import com.mcrmk.springboot.ecommerce.service.CartService;
import com.mcrmk.springboot.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    private final CartService cartService;

    @PostMapping("user/login")
    public UserResponse login(@RequestBody UserRequest request) throws Exception {
        return service.login(request);
    }

    @PostMapping("user/register")
    public UserResponse register(@RequestBody UserRequest request) throws Exception {
        return service.register(request);
    }

    @GetMapping("user/hola")
    public String userHola(){
        return cartService.list().getEmail();
    }

}