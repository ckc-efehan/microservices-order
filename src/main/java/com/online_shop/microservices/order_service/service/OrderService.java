package com.online_shop.microservices.order_service.service;

import com.online_shop.microservices.order_service.dto.OrderRequest;
import com.online_shop.microservices.order_service.model.Order;
import com.online_shop.microservices.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public void placeOrder(OrderRequest orderRequest) {
        //map OrderRequest to Order object
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setPrice(orderRequest.price());
        order.setSkuCode(orderRequest.skuCode());
        order.setQuantity(orderRequest.quantity());
        //save order to OrderRepository
        orderRepository.save(order);
    }
}
