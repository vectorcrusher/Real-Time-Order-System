package com.rtos.common.inventory;

public record InventoryReservedEvent(
        String orderId,
        String productId,
        int quantity
) {}
