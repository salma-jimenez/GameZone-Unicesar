package com.gamezone.model;

import java.time.LocalDateTime;

/**
 * Represents a sale transaction in the GameZone system.
 */
public class Sale {
    private String id;
    private LocalDateTime dateTime;
    private double totalAmount;

    public Sale(String id, LocalDateTime dateTime, double totalAmount) {
        this.id = id;
        this.dateTime = dateTime;
        this.totalAmount = totalAmount;
    }

    public String getId() { return id; }
    public LocalDateTime getDateTime() { return dateTime; }
    public double getTotalAmount() { return totalAmount; }

    /**
     * Checks if the sale is eligible for return (within 30 days).
     */
    public boolean canBeReturned() {
        return dateTime != null && !LocalDateTime.now().isAfter(dateTime.plusDays(30));
    }
}
