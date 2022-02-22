package com.mcrmk.springboot.ecommerce.service.impl;

import com.mcrmk.springboot.ecommerce.builder.OrderBuilder;
import com.mcrmk.springboot.ecommerce.exception.BadRequestException;
import com.mcrmk.springboot.ecommerce.exception.EntityNotFoundException;
import com.mcrmk.springboot.ecommerce.mail.EmailService;
import com.mcrmk.springboot.ecommerce.model.database.document.Order;
import com.mcrmk.springboot.ecommerce.model.database.document.Product;
import com.mcrmk.springboot.ecommerce.model.database.document.common.CartDetail;
import com.mcrmk.springboot.ecommerce.model.request.OrderRequest;
import com.mcrmk.springboot.ecommerce.model.response.CartDetailResponse;
import com.mcrmk.springboot.ecommerce.model.response.OrderResponse;
import com.mcrmk.springboot.ecommerce.model.response.ProductResponse;
import com.mcrmk.springboot.ecommerce.model.response.UserResponse;
import com.mcrmk.springboot.ecommerce.repository.OrderRepository;
import com.mcrmk.springboot.ecommerce.service.OrderService;
import com.mcrmk.springboot.ecommerce.service.ProductService;
import com.mcrmk.springboot.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final UserService userService;
    private final ProductService productService;
    private final EmailService emailService;
    private final OrderRepository repository;

    @Override
    public OrderResponse create(OrderRequest orderRequest) {
        if (orderRequest.getOrderDetails() == null || orderRequest.getOrderDetails().isEmpty()) {
            throw new BadRequestException("The order detail list should have at least one element");
        }
        UserResponse user = getUser();
        Order order = Order.builder()
                .address(orderRequest.getAddress())
                .userId(user.getId())
                .createdAt(new Date())
                .orderNumber((int) repository.count())
                .build();
        order.setOrderDetails(new LinkedList<>());
        List<CartDetailResponse> cartDetailResponses = new LinkedList<>();
        orderRequest.getOrderDetails().forEach(item -> {
            try {
                ProductResponse productResponse = productService.retrieve(item.getProductId());
                if (!productResponse.getIsActive())
                    throw new EntityNotFoundException(Product.class.getSimpleName(), productResponse.getId());
                Integer quantity = 0;
                if (productResponse.getStock() < item.getQuantity()) {
                    if (orderRequest.getErrorOnLowStock())
                        throw new BadRequestException("One of the items in the order list has more quantity that the amount that is available.");
                    quantity = productResponse.getStock();

                } else {
                    quantity = item.getQuantity();
                }
                order.getOrderDetails().add(new CartDetail(item.getProductId(), quantity));
                cartDetailResponses.add(new CartDetailResponse(productResponse, quantity));
                productService.updateStock(item.getProductId(), productResponse.getStock() - quantity);
            } catch (EntityNotFoundException e) {
                if (orderRequest.getErrorOnMissingItems()) throw new RuntimeException(e);
            }
        });
        Order savedOrder = repository.save(order);
        OrderResponse orderResponse = OrderBuilder.documentToResponse(savedOrder);
        orderResponse.setOrderDetails(cartDetailResponses);
        emailService.sendSimpleMessage(user.getEmail(), "Orden creada con éxito.", "Te avisamos que tu orden con id " + savedOrder.getId() + " fue creada con éxito.");
        return orderResponse;
    }

    @Override
    public List<OrderResponse> list() {
        UserResponse user = getUser();
        List<Order> ordersFromUser = repository.findByUserId(user.getId());
        List<OrderResponse> ordersFromUserResponse = ordersFromUser.stream()
                .map(this::createOrderResponse)
                .collect(Collectors.toList());
        return ordersFromUserResponse;
    }

    private OrderResponse createOrderResponse(Order order) {
        List<CartDetailResponse> detailResponse = order.getOrderDetails().stream().map(item -> {
            return new CartDetailResponse(productService.retrieve(item.getProductId()), item.getQuantity());
        }).collect(Collectors.toList());

        OrderResponse orderResponse = OrderBuilder.documentToResponse(order);
        orderResponse.setOrderDetails(detailResponse);
        return orderResponse;
    }

    private UserResponse getUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        return userService.retrieveByUsername(username);
    }


}
