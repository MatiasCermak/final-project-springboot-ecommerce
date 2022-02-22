package com.mcrmk.springboot.ecommerce.exception;

public class AuthenticationException extends RuntimeException {
    private final String message;

    public AuthenticationException(String message) {
        super(message);
        this.message = message;
    }
}
