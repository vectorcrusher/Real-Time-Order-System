package com.rtos.common.order;

import java.time.Instant;

public record OrderCreatedEvent(
        String orderId,
        String productId,
        String customerId,
        int quantity,
        Instant createdAt
) {}
