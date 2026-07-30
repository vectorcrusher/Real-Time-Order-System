package com.rtos.inventoryservice;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Slf4j
@Service
public class InventoryService {
    @Autowired
    private InventoryEventPublisher  inventoryEventPublisher;

    @Autowired
    private InventoryRepository  inventoryRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Transactional
    public void processOrder(String orderId, String productId, int quantity) {
        Inventory inventory = inventoryRepository.findById(productId).orElse(null);

        Assert.notNull(inventory, "Product could not be found");

        if (inventory.getQuantity() < quantity) {
            inventoryEventPublisher.publishOutOfStockEvent(orderId);
            return;
        }

        inventory.setQuantity(inventory.getQuantity() - quantity);
        inventoryRepository.save(inventory);

        Reservation reservation = new Reservation(orderId, productId, quantity);
        reservationRepository.save(reservation);

        log.info("Reservation has been saved: {}", reservation);

        inventoryEventPublisher.publishReserve(reservation);
    }

    @Transactional
    public void processPaymentFail(String orderId) {
        Reservation reservation = reservationRepository.findById(orderId).orElse(null);
        Assert.notNull(reservation, "Reservation could not be found");

        int quantityToRelease = reservation.getQuantity();

        Inventory inventory = inventoryRepository.findById(reservation.getProductId()).orElse(null);
        Assert.notNull(inventory, "Product could not be found");
        inventory.setQuantity(inventory.getQuantity() + quantityToRelease);
        inventoryRepository.save(inventory);
        log.info("Inventory stock for product {} has been restored: {}", reservation.getProductId(), quantityToRelease);

        reservation.setStatus(ReservationStatus.RELEASED);
        reservationRepository.save(reservation);
        log.info("Reservation has been released: {}", reservation);
    }

    @Transactional
    public void processPaymentSuccess(String orderId) {
        Reservation reservation = reservationRepository.findById(orderId).orElse(null);
        Assert.notNull(reservation, "Reservation could not be found");
        reservation.setStatus(ReservationStatus.COMPLETED);
        reservationRepository.save(reservation);
        log.info("Reservation status updated to completed: {}", reservation);
    }
}
