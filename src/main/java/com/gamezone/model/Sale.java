package com.gamezone.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.time.temporal.ChronoUnit;

/**
 * Represents a sale transaction in the GameZone system.
 */
public class Sale {

    private String idSale;
    private LocalDateTime dateTime;
    private double totalAmount;
    private List<Product> products;
    private String appliedPromotionName;
    private double discountAmount;
    private Customer customer;

    /**
     * Default constructor.
     */
    public Sale() {
        this.products = new ArrayList<>();
        this.appliedPromotionName = "Ninguna";
        this.discountAmount = 0.0;
    }

    /**
     * Constructs a Sale instance with basic parameters.
     *
     * @param id the unique identifier of the sale
     * @param dateTime the date and time of the transaction
     * @param totalAmount the initial total amount
     */
    public Sale(String id, LocalDateTime dateTime, double totalAmount) {
        this();
        this.idSale = id;
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
        this(id, dateTime, totalAmount);
        this.products = (products != null) ? products : new ArrayList<>();
    }

    public Sale(String idSale, LocalDateTime dateTime, double totalAmount, Customer customer, List<Product> products) {
        this(idSale, dateTime, totalAmount, products);
        this.customer = customer;
    }

    public String getIdSale() {
        return idSale;
    }

    public void setIdSale(String idSale) {
        this.idSale = idSale;
    }

    public String getId() {
        return idSale;
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

    public double getTotal() {
        return totalAmount;
    }

    public void setTotal(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<Product> getProducts() {
        return products;
    }

    public List<Product> getItems() {
        return products;
    }

    public List<Product> getProduct() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void setItems(List<Product> items) {
        this.products = items;
    }

    public void setProduct(List<Product> product) {
        this.products = product;
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Calculates the subtotal of the sale based on items.
     *
     * @return total price of products before discount
     */
    public double calculateSubtotal() {
        if (products == null || products.isEmpty()) {
            return totalAmount + discountAmount;
        }
        return products.stream().mapToDouble(Product::getPrice).sum();
    }

    /**
     * Generates a detailed receipt displaying subtotal, applied promotion
     * discount, and final total.
     *
     * @return formatted receipt string
     */
    public String generateReceipt() {
        double subtotal = calculateSubtotal();
        String promoName = (appliedPromotionName != null && !appliedPromotionName.isBlank())
                ? appliedPromotionName
                : "Ninguna";

        return """
               ========================================
                           RECIBO DE VENTA             
               ========================================
               ID Venta: """ + idSale + "\n"
                + "Fecha: " + dateTime + "\n"
                + "----------------------------------------\n"
                + "Subtotal: $" + String.format("%.2f", subtotal) + "\n"
                + "Descuento (" + promoName + "): -$" + String.format("%.2f", discountAmount) + "\n"
                + "Total Final: $" + String.format("%.2f", totalAmount) + "\n"
                + "========================================";
    }
    
    /**
     * Checks if the sale is eligible for a return based on the 30-day limit rule.
     *
     * @return true if the sale was made within the last 30 calendar days, false otherwise.
     */
    public boolean canBeReturned() {
        if (dateTime == null) {
            return false;
        }
        long daysBetween = ChronoUnit.DAYS.between(dateTime.toLocalDate(), java.time.LocalDate.now());
        return daysBetween >= 0 && daysBetween <= 30;
    }
}