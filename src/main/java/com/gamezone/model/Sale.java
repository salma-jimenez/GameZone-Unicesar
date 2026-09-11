package com.gamezone.model;

import java.time.LocalDateTime;

/**
 * Represents a sale transaction in the GameZone system.
 */
public class Sale {

    private String id;
    private LocalDateTime dateTime;
    private double totalAmount;

    /**
     *
     * Create a new sale using the provided identification information.
     * 
     * @param id
     * @param dateTime
     * @param totalAmount
     */
    public Sale(String id, LocalDateTime dateTime, double totalAmount) {
        this.id = id;
        this.dateTime = dateTime;
        this.totalAmount = totalAmount;
    }

    /**
     * Sets the Sale's identifier.
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Sets System local time 
     * @param dateTime
     */

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * Sets number of products to sell 
     * 
     * @param totalAmount
     */
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    /**
     * The Sale's identifier.
     *
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * System local time 
     * 
     * @return dateTime
     */
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /**
     * Number of products to sell 
     *
     * @return
     */
    public double getTotalAmount() {
        return totalAmount;
    }

    /**
     * Checks if the sale is eligible for return (within 30 days).
     */
    public boolean canBeReturned() {
        return dateTime != null && !LocalDateTime.now().isAfter(dateTime.plusDays(30));
    }
}
