package com.rtos.orderservice;

import java.math.BigDecimal;

public record CreateOrderRequest (
    String orderId,
    String customerId,
    BigDecimal amount
) {}
