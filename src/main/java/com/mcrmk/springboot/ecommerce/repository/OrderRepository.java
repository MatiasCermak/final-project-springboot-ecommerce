package com.mcrmk.springboot.ecommerce.repository;

import com.mcrmk.springboot.ecommerce.model.database.document.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {
    List<Order> findByUserId(String id);
}
