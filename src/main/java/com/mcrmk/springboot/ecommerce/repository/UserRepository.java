package com.mcrmk.springboot.ecommerce.repository;

import com.mcrmk.springboot.ecommerce.model.database.document.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);
    User findByEmail(String username);
}
