package com.mcrmk.springboot.ecommerce.builder;

import com.mcrmk.springboot.ecommerce.model.database.document.User;
import com.mcrmk.springboot.ecommerce.model.request.UserRequest;
import com.mcrmk.springboot.ecommerce.model.response.UserResponse;

public class UserBuilder {

    public static User requestToDocument(UserRequest request) {
        return User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(request.getPassword())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phone(request.getPhone())
                .isAdmin(request.getIsAdmin())
                .build();
    }

    public static UserResponse documentToResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }
}
