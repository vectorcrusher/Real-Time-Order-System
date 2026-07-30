package com.rtos.orderservice;

import com.rtos.common.inventory.InventoryNotFound;
import com.rtos.common.inventory.InventoryOutOfStockEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderStatusListener {

    private static final Logger log = LoggerFactory.getLogger(OrderStatusListener.class);
    private final OrderRepository orderRepository;

    public OrderStatusListener(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @KafkaListener(topics = "inventory-events", groupId = "order-service")
    @KafkaHandler
    public void handleOutOfStock(InventoryOutOfStockEvent event) {
        updateStatus(event.orderId(), OrderStatus.FAILED);
    }

    @KafkaListener(topics = "inventory-events", groupId = "order-service")
    @KafkaHandler
    public void handleNotFound(InventoryNotFound event) {
        updateStatus(event.orderId(), OrderStatus.FAILED);
    }

//    @KafkaListener(topics = "payment-events", groupId = "order-service")
//    @KafkaHandler
//    public void handlePaymentProcessed(PaymentProcessedEvent event) {
//        updateStatus(event.productId(), OrderStatus.COMPLETED);
//    }
//
//    @KafkaHandler
//    public void handlePaymentFailed(PaymentFailedEvent event) {
//        updateStatus(event.productId(), OrderStatus.FAILED);
//    }

    private void updateStatus(String orderId, OrderStatus status) {
        orderRepository.findById(orderId).ifPresent(order -> {
            order.setStatus(status);
            orderRepository.save(order);
            log.info("Order {} marked {} — {}", orderId, status);
        });
    }
}
