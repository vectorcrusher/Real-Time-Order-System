package com.rtos.common.inventory;

public record InventoryNotFound(
        String orderId,
        String productId
) {}
