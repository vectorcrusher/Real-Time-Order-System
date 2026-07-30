package com.rtos.orderservice;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "orders")
public class Order {

    @Id
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
