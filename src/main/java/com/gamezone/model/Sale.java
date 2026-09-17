package com.gamezone.model;

import java.time.LocalDateTime;
<<<<<<< HEAD
import java.util.ArrayList;
=======
>>>>>>> origin/develop
import java.util.List;

/**
 * Represents a sale transaction in the GameZone system.
 */
public class Sale {

    private String idSale;
    private LocalDateTime dateTime;
    private double totalAmount;
<<<<<<< HEAD
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
=======
    private Customer customer;
    private List<Product> listproduct;

    public Sale(String idSale, LocalDateTime dateTime, double totalAmount, Customer customer, List<Product> listproduct) {
        this.idSale = idSale;
>>>>>>> origin/develop
        this.dateTime = dateTime;
        this.totalAmount = totalAmount;
        this.customer = customer;
        this.listproduct = listproduct;
    }

    public String getIdSale() {
        return idSale;
    }

    public void setIdSale(String idVenta) {
        this.idSale= idVenta;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

<<<<<<< HEAD
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
    
=======
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Product> getProduct() {
        return listproduct;
    }

    public void setProduct(List<Product> product) {
        this.listproduct = product;
    }
>>>>>>> origin/develop

    /**
     * Checks if the sale is eligible for return (within 30 days).
     */
    void CaculateTotalSales(){
        for ( Product product : listproduct) {
            totalAmount += product.getPrice();
        }
    }
    
<<<<<<< HEAD
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
=======
    
}
>>>>>>> origin/develop
