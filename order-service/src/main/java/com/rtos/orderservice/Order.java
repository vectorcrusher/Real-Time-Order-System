package com.rtos.orderservice;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Data
@Table(name = "orders")
public class Order {

    @Id
    private String id;
    private String customerId;
    private int quantity;

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // PENDING, CONFIRMED, FAILED
    private Instant createdAt;

    protected Order() {}

    public Order(String id, String productId, String customerId, int quantity) {
        this.id = id;
        this.customerId = customerId;
        this.quantity = quantity;
        this.status = OrderStatus.PENDING;
        this.createdAt = Instant.now();
    }
}
