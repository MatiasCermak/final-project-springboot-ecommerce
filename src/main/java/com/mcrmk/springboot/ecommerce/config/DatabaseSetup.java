package com.mcrmk.springboot.ecommerce.config;

import com.mcrmk.springboot.ecommerce.model.request.CategoryRequest;
import com.mcrmk.springboot.ecommerce.model.request.ProductRequest;
import com.mcrmk.springboot.ecommerce.model.request.UserRequest;
import com.mcrmk.springboot.ecommerce.service.CategoryService;
import com.mcrmk.springboot.ecommerce.service.ProductService;
import com.mcrmk.springboot.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DatabaseSetup implements ApplicationRunner {

    private final UserService userService;
    private final CategoryService categoryService;
    private final ProductService productService;

    public static void main(String[] args) {
        SpringApplication.run(DatabaseSetup.class, args);
    }
    @Override
    public void run(ApplicationArguments args) throws Exception {
        createUsers();
        createCategories();
        createProducts();
    }

    private void createUsers(){
        UserRequest user1 = UserRequest.builder()
                .email("admin@admin.com")
                .username("admin")
                .firstName("Admin")
                .lastName("Admin")
                .isAdmin(true)
                .password("admin")
                .passwordCheck("admin")
                .phone("112233445566")
                .build();
        UserRequest user2 = UserRequest.builder()
                .email("matias.cermak99@gmail.com")
                .username("Mcermak")
                .firstName("Matias")
                .lastName("Cermak")
                .isAdmin(false)
                .password("mcermak")
                .passwordCheck("mcermak")
                .phone("112233445566")
                .build();
        UserRequest user3 = UserRequest.builder()
                .email("matrox2300@gmail.com")
                .username("Matrox")
                .firstName("Matthew")
                .lastName("Cermak")
                .isAdmin(false)
                .password("matrox")
                .passwordCheck("matrox")
                .phone("112233445566")
                .build();
        userService.register(user1);
        userService.register(user2);
        userService.register(user3);
    }

    private void createCategories(){
        CategoryRequest category1 = CategoryRequest.builder().name("Notebooks").build();
        CategoryRequest category2 = CategoryRequest.builder().name("Consolas").build();
        CategoryRequest category3 = CategoryRequest.builder().name("Smartphones").build();
        categoryService.create(category1);
        categoryService.create(category2);
        categoryService.create(category3);
    }

    private void createProducts(){
        ProductRequest product1 = ProductRequest.builder()
                .title("Asus I7 12800k 16GB RAM 1TB SSD")
                .categoryId(categoryService.retrieveByName("Notebooks").getId())
                .description("This is a cool notebook")
                .unitPrice(2000L)
                .stock(10)
                .imageUrl("https://www.necxus.com.ar/products_image/23210/1000x1000_1.jpg")
                .active(true)
                .build();
        ProductRequest product2 = ProductRequest.builder()
                .title("PlayStation 5")
                .categoryId(categoryService.retrieveByName("Consolas").getId())
                .description("Play 5 is cool")
                .unitPrice(500L)
                .stock(15)
                .imageUrl("https://i.blogs.es/86b11e/ps51/1366_2000.jpeg")
                .active(true)
                .build();
        ProductRequest product3 = ProductRequest.builder()
                .title("IPhone 13")
                .categoryId(categoryService.retrieveByName("Smartphones").getId())
                .description("This is a cool phone")
                .unitPrice(2000L)
                .stock(10)
                .imageUrl("https://arsonyb2c.vtexassets.com/arquivos/ids/356995/PlayStation-5-DualShock.jpg?v=637586444113030000")
                .active(true)
                .build();
        productService.create(product1);
        productService.create(product2);
        productService.create(product3);
    }
}
