package com.gamezone.model;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Represents a sale transaction in the GameZone system.
 */
public class Sale {

    private String idSale;
    private LocalDateTime dateTime;
    private double totalAmount;
    private Customer customer;
    private List<Product> listproduct;

    public Sale(String idSale, LocalDateTime dateTime, double totalAmount, Customer customer, List<Product> listproduct) {
        this.idSale = idSale;
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

    /**
     * Checks if the sale is eligible for return (within 30 days).
     */
    void CaculateTotalSales(){
        for ( Product product : listproduct) {
            totalAmount += product.getPrice();
        }
    }
    
    
}