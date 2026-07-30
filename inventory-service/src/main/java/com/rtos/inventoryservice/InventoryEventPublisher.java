package com.rtos.inventoryservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class InventoryEventPublisher {
    @Autowired
    private KafkaTemplate<String, Reservation> kafkaTemplate;

    @Autowired
    private KafkaTemplate<String, String> kafkaOutOfStockTemplate;

    public void publishReserve(Reservation reservation) {
        kafkaTemplate.send("inventory-reserved", reservation.getOrderId(), reservation);

        log.info("Reservation message published");
    }

    public void publishOutOfStockEvent(String orderId) {
        kafkaOutOfStockTemplate.send("inventory-out-of-stock", orderId);
        log.info("OutOfStock message published");
    }
}
