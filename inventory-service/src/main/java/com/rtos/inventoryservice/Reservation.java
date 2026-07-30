package com.rtos.inventoryservice;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Entity
@Table(name= "reservation")
@Data
public class Reservation {
    @Id
    private String orderId;
    private String productId;
    private int quantity;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    private Instant createdAt;

    public Reservation(String orderId, String productId, int quantity) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.status = ReservationStatus.RESERVED;
        this.createdAt = Instant.now();
    }
}
