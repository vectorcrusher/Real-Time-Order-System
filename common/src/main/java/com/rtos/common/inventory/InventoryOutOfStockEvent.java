package com.rtos.common.inventory;

public record InventoryOutOfStockEvent(
        String orderId,
        String productId,
        String reason
) {}
