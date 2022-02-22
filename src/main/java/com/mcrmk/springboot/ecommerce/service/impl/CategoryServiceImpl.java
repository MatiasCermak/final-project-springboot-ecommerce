package com.mcrmk.springboot.ecommerce.service.impl;

import com.mcrmk.springboot.ecommerce.builder.CategoryBuilder;
import com.mcrmk.springboot.ecommerce.cache.impl.CacheClientImpl;
import com.mcrmk.springboot.ecommerce.exception.BadRequestException;
import com.mcrmk.springboot.ecommerce.exception.EntityNotFoundException;
import com.mcrmk.springboot.ecommerce.model.database.document.Category;
import com.mcrmk.springboot.ecommerce.model.request.CategoryRequest;
import com.mcrmk.springboot.ecommerce.model.response.CategoryResponse;
import com.mcrmk.springboot.ecommerce.repository.CategoryRepository;
import com.mcrmk.springboot.ecommerce.service.CategoryService;
import com.mcrmk.springboot.ecommerce.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CacheClientImpl<Category> cache;
    private final CategoryRepository repository;

    @Override
    public CategoryResponse create(CategoryRequest categoryRequest) {
        if(categoryRequest.getName() == null || Objects.equals(categoryRequest.getName(), "")){
            throw new BadRequestException("El campo de name es inválido o no existe");
        }
        Category categoryCreated = repository.save(CategoryBuilder.requestToDocument(categoryRequest));
        cache.save(Constants.CATEGORY_KEY, categoryCreated.getId(), categoryCreated);
        return CategoryBuilder.documentToResponse(categoryCreated);
    }

    @Override
    public CategoryResponse retrieve(String id) {
        return CategoryBuilder.documentToResponse(getFromCacheOrDB(id));
    }

    @Override
    public CategoryResponse retrieveByName(String name) {
        Category categoryFound = repository.findByName(name);
        if(categoryFound == null){
            throw new EntityNotFoundException(Category.class.getSimpleName(), name);
        }
        cache.save(Constants.CATEGORY_KEY, categoryFound.getId(), categoryFound);
        return CategoryBuilder.documentToResponse(categoryFound);
    }

    @Override
    public List<CategoryResponse> list() {
        List<CategoryResponse> categoryList = repository.findAll().stream()
                .map(CategoryBuilder::documentToResponse)
                .collect(Collectors.toList());
        return categoryList;
    }

    @Override
    public CategoryResponse update(CategoryRequest categoryRequest, String id) {
        if(categoryRequest.getName() == null){
            throw new BadRequestException("El campo de name es inválido o no existe");
        }
        getFromCacheOrDB(id);
        Category categoryToUpdate = CategoryBuilder.requestToDocument(categoryRequest);
        categoryToUpdate.setId(id);
        Category categoryUpdated = repository.save(categoryToUpdate);
        cache.save(Constants.CATEGORY_KEY, id, categoryUpdated);
        return CategoryBuilder.documentToResponse(categoryUpdated);
    }

    @Override
    public CategoryResponse delete(String id) {
        Category categoryToDelete = getFromCacheOrDB(id);
        repository.delete(categoryToDelete);
        return CategoryBuilder.documentToResponse(categoryToDelete);
    }

    private Category getFromCacheOrDB(String id){
        Category categoryFromCache = cache.recover(Constants.CATEGORY_KEY, id, Category.class);
        if(categoryFromCache != null) return categoryFromCache;
        Category categoryFromDB = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(Category.class.getSimpleName(), id)); //CAMBIAR A EXCEPTION DESPUES
        cache.save(Constants.CATEGORY_KEY, categoryFromDB.getId(), categoryFromDB);
        return categoryFromDB;
    }


}
