package com.gamezone.model;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Represents a sale transaction in the GameZone system.
 */
public class Sale {
    private String id;
    private LocalDateTime dateTime;
    private double totalAmount;
    private List<Product> products;
    
    public Sale(String id, LocalDateTime dateTime, double totalAmount) {
        this.id = id;
        this.dateTime = dateTime;
        this.totalAmount = totalAmount;
    }

    public Sale(String id, LocalDateTime dateTime, double totalAmount, List<Product> products) {
        this.id = id;
        this.dateTime = dateTime;
        this.totalAmount = totalAmount;
        this.products = products;
    }
    
    

    public String getId() { return id; }
    public LocalDateTime getDateTime() { return dateTime; }
    public double getTotalAmount() { return totalAmount; }
    public List<Product> getProducts(){
        return products;
    }

    /**
     * Checks if the sale is eligible for return (within 30 days).
     */
    public boolean canBeReturned() {
        return dateTime != null && !LocalDateTime.now().isAfter(dateTime.plusDays(30));
    }
}
