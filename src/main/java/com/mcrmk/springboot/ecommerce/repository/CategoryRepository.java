package com.mcrmk.springboot.ecommerce.repository;

import com.mcrmk.springboot.ecommerce.model.database.document.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository  extends MongoRepository<Category, String> {
    Category findByName(String name);
}
