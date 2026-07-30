package com.rtos.orderservice;


public record CreateOrderRequest (
    String productId,
    String customerId,
    int quantity
) {}
