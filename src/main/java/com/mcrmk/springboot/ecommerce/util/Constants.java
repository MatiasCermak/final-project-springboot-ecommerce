package com.mcrmk.springboot.ecommerce.util;

public class Constants {
    //Redis Keys
    public static final String USER_TOKEN_KEY = "user-token";
    public static final String USER_KEY = "user";
    public static final String PRODUCT_KEY = "product";
    public static final String CATEGORY_KEY = "category";
    public static final String CART_KEY = "cart";
    public static final String ORDER_KEY = "order";


    //Security
    public static final String ROLE = "ROLE_USER";
    public static final String AUTHORITIES = "authorities";
    public static final String HEADER_TOKEN = "Authorization";
    public static final String PREFIX_TOKEN = "Bearer ";
}
