package com.rtos.orderservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody CreateOrderRequest request) {
        String orderId = UUID.randomUUID().toString();
        Order order = new Order(orderId, request.customerId(), request.amount());

        //orderRepository.save(order);

        OrderCreatedEvent event = new OrderCreatedEvent(
                orderId, request.customerId(), request.amount(), Instant.now()
        );

        // key by orderId so all events for this order land on the same partition
        kafkaTemplate.send("order-events", orderId, event);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(order);
    }
}
