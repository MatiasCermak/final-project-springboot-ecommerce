package com.mcrmk.springboot.ecommerce.builder;

import com.mcrmk.springboot.ecommerce.model.database.document.Cart;
import com.mcrmk.springboot.ecommerce.model.database.document.Product;
import com.mcrmk.springboot.ecommerce.model.response.CartResponse;
import com.mcrmk.springboot.ecommerce.model.response.ProductResponse;

public class CartBuilder {

    public static CartResponse documentToResponse(Cart cart) {
        return CartResponse.builder()
                .userId(cart.getId())
                .email(cart.getEmail())
                .dateModified(cart.getDateModified())
                .build();
    }

}
