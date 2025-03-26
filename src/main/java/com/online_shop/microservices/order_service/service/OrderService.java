package com.online_shop.microservices.order_service.service;

import com.online_shop.microservices.order_service.client.InventoryClient;
import com.online_shop.microservices.order_service.dto.OrderRequest;
import com.online_shop.microservices.order_service.event.OrderPlacedEvent;
import com.online_shop.microservices.order_service.model.Order;
import com.online_shop.microservices.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    public void placeOrder(OrderRequest orderRequest) {
        var isProductInStock = inventoryClient.isInStock(orderRequest.skuCode(), orderRequest.quantity());

        if(isProductInStock) {
            //map OrderRequest to Order object
            Order order = new Order();
            order.setOrderNumber(UUID.randomUUID().toString());
            order.setPrice(orderRequest.price());
            order.setSkuCode(orderRequest.skuCode());
            order.setQuantity(orderRequest.quantity());
            //save order to OrderRepository
            orderRepository.save(order);

            //send the message to Kafka topic
            //ordernumber, email
            OrderPlacedEvent orderPlacedEvent = new OrderPlacedEvent(order.getOrderNumber(), orderRequest.userDetails().email());
            log.info("Start - Sending OrderPlacedEvent {} to Kafka topic order-placed" , orderPlacedEvent);
            kafkaTemplate.send("order-placed", orderPlacedEvent);
            log.info("End - Sending OrderPlacedEvent {} to Kafka topic order-placed" , orderPlacedEvent);

        } else {
            throw new RuntimeException("Product with SkuCode " + orderRequest.skuCode() + " is out of stock");
        }
    }
}
