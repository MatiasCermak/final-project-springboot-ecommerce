package com.mcrmk.springboot.ecommerce.service.impl;

import com.mcrmk.springboot.ecommerce.builder.CartBuilder;
import com.mcrmk.springboot.ecommerce.cache.impl.CacheClientImpl;
import com.mcrmk.springboot.ecommerce.exception.EntityNotFoundException;
import com.mcrmk.springboot.ecommerce.model.database.document.Cart;
import com.mcrmk.springboot.ecommerce.model.database.document.Product;
import com.mcrmk.springboot.ecommerce.model.database.document.common.CartDetail;
import com.mcrmk.springboot.ecommerce.model.response.CartDetailResponse;
import com.mcrmk.springboot.ecommerce.model.response.CartResponse;
import com.mcrmk.springboot.ecommerce.model.response.ProductResponse;
import com.mcrmk.springboot.ecommerce.model.response.UserResponse;
import com.mcrmk.springboot.ecommerce.repository.CartRepository;
import com.mcrmk.springboot.ecommerce.service.CartService;
import com.mcrmk.springboot.ecommerce.service.ProductService;
import com.mcrmk.springboot.ecommerce.service.UserService;
import com.mcrmk.springboot.ecommerce.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedList;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CacheClientImpl<Cart> cache;
    private final UserService userService;
    private final ProductService productService;
    private final CartRepository repository;

    @Override
    public Cart create(UserResponse user) {
        Cart newCart = Cart.builder()
                .id(user.getId())
                .userId(user.getId())
                .email(user.getEmail())
                .cartDetails(new LinkedList<CartDetail>())
                .dateModified(new Date())
                .build();
        log.info(newCart.toString());
        return saveToCacheAndDB(newCart);

    }

    @Override
    public ProductResponse addProduct(CartDetail cartDetail) {
        Cart cartFromUser = getCartFromUser();
        ProductResponse productToAdd = productService.retrieve(cartDetail.getProductId());
        if (!productToAdd.getIsActive()) {
            throw new EntityNotFoundException(Product.class.getSimpleName(), productToAdd.getId());
        }
        cartFromUser.getCartDetails().add(new CartDetail(cartDetail.getProductId(), cartDetail.getQuantity()));
        saveToCacheAndDB(cartFromUser);
        return productToAdd;
    }

    @Override
    public CartResponse list() {
        Cart cartFromUser = getCartFromUser();
        CartResponse cartFromUserResponse = CartBuilder.documentToResponse(cartFromUser);
        cartFromUserResponse.setCartDetails( new LinkedList<CartDetailResponse>());
        cartFromUser.getCartDetails().forEach(item -> {
            try {
                ProductResponse product = productService.retrieve(item.getProductId());
                if (!product.getIsActive()) {
                    throw new EntityNotFoundException(Product.class.getSimpleName(), product.getId());
                }
                cartFromUserResponse.getCartDetails().add(new CartDetailResponse(product, item.getQuantity()));
            } catch (EntityNotFoundException e) {
                this.delete(item.getProductId());
                cartFromUserResponse.setMissingItems(true);
            }
        });
        return cartFromUserResponse;
    }

    @Override
    public CartResponse update(String productId, Integer quantity) {
        Cart cartFromUser = getCartFromUser();
        cartFromUser.setCartDetails(cartFromUser.getCartDetails().stream().map(item -> {
            if (Objects.equals(item.getProductId(), productId) && quantity >= 0) item.setQuantity(quantity);
            return item;
        }).collect(Collectors.toList()));
        saveToCacheAndDB(cartFromUser);
        return this.list();
    }

    @Override
    public void delete(String productId) {
        Cart cartFromUser = getCartFromUser();
        cartFromUser.setCartDetails(cartFromUser.getCartDetails().stream().filter(item -> !Objects.equals(item.getProductId(), productId)).collect(Collectors.toList()));
        saveToCacheAndDB(cartFromUser);
    }

    @Override
    public CartResponse clear() {
        Cart cartFromUser = getCartFromUser();
        cartFromUser.getCartDetails().clear();
        saveToCacheAndDB(cartFromUser);
        return this.list();
    }

    private Cart getCartFromUser() {
        UserResponse user = getUser();
        return getFromCacheOrDBOrCreate(user);
    }

    private UserResponse getUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        return userService.retrieveByUsername(username);
    }

    private Cart saveToCacheAndDB(Cart cart){
        cart.setDateModified(new Date());
        Cart savedCart = repository.save(cart);
        cache.save(Constants.CART_KEY, savedCart.getId(), savedCart);
        return savedCart;
    }

    private Cart getFromCacheOrDBOrCreate(UserResponse user){
        Cart cartFromCache = cache.recover(Constants.CART_KEY, user.getId(), Cart.class);
        if(cartFromCache != null) return cartFromCache;
        Cart cartFromDB = repository.findById(user.getId()).orElse(create(user));
        cache.save(Constants.CART_KEY, cartFromDB.getId(), cartFromDB);
        return cartFromDB;
    }

}
