package com.rtos.orderservice;


import java.math.BigDecimal;
import java.time.Instant;

public record OrderCreatedEvent(
        String orderId,
        String customerId,
        BigDecimal amount,
        Instant createdAt
) {}
