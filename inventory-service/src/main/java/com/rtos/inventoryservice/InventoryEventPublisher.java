package com.rtos.inventoryservice;

import com.rtos.common.inventory.InventoryNotFound;
import com.rtos.common.inventory.InventoryOutOfStockEvent;
import com.rtos.common.inventory.InventoryReservedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class InventoryEventPublisher {

    private static final String TOPIC = "inventory-events";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public InventoryEventPublisher(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishReserved(String orderId, String productId, int quantity) {
        InventoryReservedEvent event = new InventoryReservedEvent(orderId, productId, quantity);
        send(orderId, event);
    }

    public void publishOutOfStock(String orderId, String productId, String reason) {
        InventoryOutOfStockEvent event = new InventoryOutOfStockEvent(orderId, productId, reason);
        send(orderId, event);
    }

    public void publishProductNotFound(String orderId, String productId) {
        InventoryNotFound event = new InventoryNotFound(orderId, productId);
        send(orderId, event);
    }

    private void send(String key, Object event) {
        kafkaTemplate.send(TOPIC, key, event).whenComplete((result, ex) -> {
            if (ex != null) {
                log.error("Failed to publish event {} for key {}", event.getClass().getSimpleName(), key, ex);
            } else {
                log.info("Published {} for key {} to partition {}",
                        event.getClass().getSimpleName(), key,
                        result.getRecordMetadata().partition());
            }
        });
    }
}