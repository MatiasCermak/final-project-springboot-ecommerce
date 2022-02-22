package com.mcrmk.springboot.ecommerce.service.impl;

import com.mcrmk.springboot.ecommerce.builder.ProductBuilder;
import com.mcrmk.springboot.ecommerce.cache.impl.CacheClientImpl;
import com.mcrmk.springboot.ecommerce.exception.EntityNotFoundException;
import com.mcrmk.springboot.ecommerce.model.database.document.Product;
import com.mcrmk.springboot.ecommerce.model.request.ProductRequest;
import com.mcrmk.springboot.ecommerce.model.response.CategoryResponse;
import com.mcrmk.springboot.ecommerce.model.response.ProductResponse;
import com.mcrmk.springboot.ecommerce.repository.ProductRepository;
import com.mcrmk.springboot.ecommerce.service.CategoryService;
import com.mcrmk.springboot.ecommerce.service.ProductService;
import com.mcrmk.springboot.ecommerce.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final CategoryService categoryService;
    private final CacheClientImpl<Product> cache;

    @Override
    public ProductResponse create(ProductRequest productRequest) {
        Product productToCreate = ProductBuilder.requestToDocument(productRequest);
        Product productCreated = repository.save(productToCreate);
        cache.save(Constants.PRODUCT_KEY, productCreated.getId(), productCreated);
        return createResponse(productCreated);
    }

    @Override
    public ProductResponse retrieve(String id) {
        Product productRetrieved = getFromCacheOrDB(id);
        return createResponse(productRetrieved);
    }

    @Override
    public List<ProductResponse> list() {
        List<ProductResponse> productList = repository.findByActive(true).stream()
                .map(this::createResponse)
                .collect(Collectors.toList());
        return productList;
    }

    @Override
    public List<ProductResponse> listAdmin() {

        List<ProductResponse> productList = repository.findAll().stream()
                .map(this::createResponse)
                .collect(Collectors.toList());
        return productList;
    }

    @Override
    public List<ProductResponse> listByCategory(String categoryName){
        CategoryResponse foundCategory = categoryService.retrieveByName(categoryName);
        List<ProductResponse> productList = repository.findByCategoryId(foundCategory.getId())
                .stream()
                .map(this::createResponse)
                .collect(Collectors.toList());
        return productList;
    }

    @Override
    public ProductResponse update(ProductRequest productRequest, String id) {
        getFromCacheOrDB(id);
        Product productToUpdate = ProductBuilder.requestToDocument(productRequest);
        productToUpdate.setId(id);
        if (productToUpdate.getStock() == 0){
            productToUpdate.setActive(false);
        }
        Product productUpdated = repository.save(productToUpdate);
        cache.save(Constants.PRODUCT_KEY, productUpdated.getId(), productUpdated);
        return createResponse(productUpdated);
    }

    @Override
    public ProductResponse delete(String id) {
        Product productToDelete = getFromCacheOrDB(id);
        productToDelete.setActive(false);
        repository.save(productToDelete);
        return createResponse(productToDelete);
    }

    public ProductResponse updateStock(String id, Integer quantity){
        Product productToUpdate = getFromCacheOrDB(id);
        productToUpdate.setStock(quantity);
        Product productUpdated = repository.save(productToUpdate);
        cache.save(Constants.PRODUCT_KEY, productUpdated.getId(), productUpdated);
        return ProductBuilder.documentToResponse(productUpdated);
    }

    private ProductResponse createResponse(Product product){
        ProductResponse productResponse = ProductBuilder.documentToResponse(product);
        CategoryResponse categorySelected = categoryService.retrieve(product.getCategoryId());
        productResponse.setCategory(categorySelected);
        return productResponse;
    }

    private Product getFromCacheOrDB(String id){
        Product productFromCache = cache.recover(Constants.PRODUCT_KEY, id, Product.class);
        if(productFromCache != null) return productFromCache;
        Product productFromDB = repository.findById(id).orElseThrow( () -> new EntityNotFoundException(Product.class.getSimpleName(), id));
        cache.save(Constants.PRODUCT_KEY, productFromDB.getId(), productFromDB);
        return productFromDB;
    }

}
