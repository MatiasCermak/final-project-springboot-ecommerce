package com.mcrmk.springboot.ecommerce.repository;

import com.mcrmk.springboot.ecommerce.model.database.document.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends MongoRepository<Cart, String> {
}
