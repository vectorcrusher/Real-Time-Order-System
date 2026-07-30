package com.rtos.inventoryservice;

import com.rtos.common.order.OrderCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class InventoryEventListener {

    private static final Logger log = LoggerFactory.getLogger(InventoryEventListener.class);

    @Autowired
    private InventoryService inventoryService;

    @KafkaListener(topics = "order-events", groupId = "inventory-service")
    public void handleOrderCreated(OrderCreatedEvent event) {
        log.info("Inventory Service received: {}", event);

        inventoryService.processOrder(event.orderId(), event.productId(), event.quantity());
    }

    @KafkaListener(topics = "payment-events", groupId = "inventory-service")
    @KafkaHandler
    public void handlePaymentFail(OrderCreatedEvent event) {
        log.info("Inventory Service received: {}", event);

    }
}
