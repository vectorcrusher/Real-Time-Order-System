package com.rtos.inventoryservice;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name= "inventory")
public class Inventory {
    @Id
    private String productId;

    private int quantity;
}
