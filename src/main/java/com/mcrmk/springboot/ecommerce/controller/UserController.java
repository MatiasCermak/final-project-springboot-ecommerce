package com.mcrmk.springboot.ecommerce.controller;

import com.mcrmk.springboot.ecommerce.model.request.UserRequest;
import com.mcrmk.springboot.ecommerce.model.response.UserResponse;
import com.mcrmk.springboot.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping("user/login")
    public UserResponse login(@RequestBody UserRequest request) throws Exception {
        return service.login(request);
    }

    @PostMapping("user/register")
    public UserResponse register(@RequestBody UserRequest request) throws Exception {
        return service.register(request);
    }

}