package com.mcrmk.springboot.ecommerce.service;

import com.mcrmk.springboot.ecommerce.model.database.document.Cart;
import com.mcrmk.springboot.ecommerce.model.database.document.common.CartDetail;
import com.mcrmk.springboot.ecommerce.model.response.CartResponse;
import com.mcrmk.springboot.ecommerce.model.response.ProductResponse;
import com.mcrmk.springboot.ecommerce.model.response.UserResponse;

public interface CartService {

    Cart create(UserResponse user);
    ProductResponse addProduct(CartDetail cartDetail);
    CartResponse list();
    CartResponse update(String productId, Integer quantity);
    void delete(String productId);
    CartResponse clear();


}
