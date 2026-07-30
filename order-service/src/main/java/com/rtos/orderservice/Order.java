package com.rtos.orderservice;

import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
public class Order {

    private String id;
    private String customerId;
    private BigDecimal amount;
    private OrderStatus status; // PENDING, CONFIRMED, FAILED
    private Instant createdAt;

    protected Order() {}

    public Order(String id, String customerId, BigDecimal amount) {
        this.id = id;
        this.customerId = customerId;
        this.amount = amount;
        this.status = OrderStatus.PENDING;
        this.createdAt = Instant.now();
    }
}
