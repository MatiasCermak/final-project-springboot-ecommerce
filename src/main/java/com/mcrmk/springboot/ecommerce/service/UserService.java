package com.mcrmk.springboot.ecommerce.service;

import com.mcrmk.springboot.ecommerce.model.request.UserRequest;
import com.mcrmk.springboot.ecommerce.model.response.UserResponse;

public interface UserService {
    UserResponse login(UserRequest request);
    UserResponse register(UserRequest request);
    UserResponse retrieveByUsername(String username);
}
