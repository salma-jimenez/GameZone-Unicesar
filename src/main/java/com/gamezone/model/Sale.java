package com.gamezone.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a sale transaction in the GameZone system.
 */
public class Sale {
    private String id;
    private LocalDateTime dateTime;
    private double totalAmount;
    private List<Product> products;
    private String appliedPromotionName;
    private double discountAmount;
    
    /**
     * Constructs a Sale instance with basic parameters.
     * 
     * @param id the unique identifier of the sale
     * @param dateTime the date and time of the transaction
     * @param totalAmount the initial total amount
     */
    public Sale(String id, LocalDateTime dateTime, double totalAmount) {
        this.id = id;
        this.dateTime = dateTime;
        this.totalAmount = totalAmount;
    }

    /**
     * Constructs a Sale instance with full product list.
     * 
     * @param id the unique identifier of the sale
     * @param dateTime the date and time of the transaction
     * @param totalAmount the initial total amount
     * @param products the list of products included in the sale
     */
    public Sale(String id, LocalDateTime dateTime, double totalAmount, List<Product> products) {
        this.id = id;
        this.dateTime = dateTime;
        this.totalAmount = totalAmount;
        this.products = (products != null) ? products : new ArrayList<>();
        this.appliedPromotionName = "Ninguna";
        this.discountAmount = 0.0;
    }
    
    public String getId() { return id; }
    public LocalDateTime getDateTime() { return dateTime; }
    public double getTotalAmount() { return totalAmount; }
    public List<Product> getProducts(){
        return products;
    }
    public String getAppliedPromotionName() {
        return appliedPromotionName;
    }
    public void setAppliedPromotionName(String appliedPromotionName) {
        this.appliedPromotionName = appliedPromotionName;
    }
    public double getDiscountAmount() {
        return discountAmount;
    }
    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }
    

    /**
     * Checks if the sale is eligible for return (within 30 days).
     */
    public boolean canBeReturned() {
        return dateTime != null && !LocalDateTime.now().isAfter(dateTime.plusDays(30));
    }
    
    /**
     * Generates a detailed receipt displaying subtotal, applied promotion discount, and final total.
     * 
     * @return formatted receipt string
     */
    public String generateReceipt() {
        double subtotal = this.totalAmount + this.discountAmount;
        String promoName = (appliedPromotionName != null && !appliedPromotionName.isBlank()) 
                ? appliedPromotionName 
                : "Ninguna";

        return """
               ========================================
                          RECIBO DE VENTA              
               ========================================
               ID Venta: """ + id + "\n"
             + "Fecha: " + dateTime + "\n"
             + "----------------------------------------\n"
             + "Subtotal: $" + String.format("%.2f", subtotal) + "\n"
             + "Descuento (" + promoName + "): -$" + String.format("%.2f", discountAmount) + "\n"
             + "Total Final: $" + String.format("%.2f", totalAmount) + "\n"
             + "========================================";
    }
}
