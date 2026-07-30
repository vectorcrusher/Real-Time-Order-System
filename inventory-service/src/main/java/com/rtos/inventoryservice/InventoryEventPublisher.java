package com.rtos.inventoryservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class InventoryEventPublisher {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void publishSuccessEvent(String topic, String event) {
        kafkaTemplate.send(topic, event);
    }

    public void publishFailureEvent(String topic, String event) {
        kafkaTemplate.send(topic, event);
    }
}
