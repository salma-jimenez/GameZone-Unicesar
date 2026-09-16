
package com.gamezone.model;

import java.time.LocalDate;

/**
 * It represents the product warranty associated with the sale of the product.
 * This class is abstract because there are various reasons for providing 
 * a warranty for a specific product. 
 *
 * @author Salomejimenez
 */
public abstract class Warranty {

    private String idWarranty;
    private Product product;
    private Sale sale;
    private LocalDate startDate;
    private LocalDate endDate;

    /**
     *
     * @param idWarranty the warranty's unique identifier
     * @param product product under warranty 
     * @param sale purchase of the product
     * @param startDate the date the warranty starts
     */
    public Warranty(String idWarranty, Product product, Sale sale, LocalDate startDate) {    
        this.idWarranty = idWarranty;
        this.product = product;
        this.sale = sale;
        this.startDate = startDate;
        this.endDate = startDate.plusMonths(this.getDurationInMonths());
    }
    /**
     * Product for which you would like to add a warranty
     * 
     * @return product
     */
    public Product getProduct() {
        return product;
    }
    /**
     * 
     * sets Product for which you would like to add a warranty
     * @param product 
     */

    public void setProduct(Product product) {
        this.product = product;
    }
    /**
     * Product sale with warranty 
     * 
     * @return sale
     */
    public Sale getSale() {
        return sale;
    }
    /**
     * sets Product sale with warranty 
     * 
     * @param sale 
     */
    public void setSale(Sale sale) {
        this.sale = sale;
    }

    /**
     * the warranty's identifier.
     *
     * @return idWarranty
     */
    public String getIdWarranty() {
        return idWarranty;
    }

    /**
     * Sets the warranty's identifier.
     *
     * @param idWarranty
     */
    public void setIdWarranty(String idWarranty) {
        this.idWarranty = idWarranty;
    }

    /**
     * system local time 
     * 
     * @return the date the warranty starts
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * @return the date the warranty ends
     */
    public LocalDate getEndDate() {
        return endDate;
    }
    /**
     * 
     * @param startDate 
     */

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    /**
     * 
     * @param endDate 
     */
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    
    
    /**
     * Checks whether this warranty is still valid on the given date
     *
     * @param date the date to check against
     * @return true if date falls within [startDate, endDate]
     */
    public boolean isActive(LocalDate date) {
        return !date.isBefore(startDate) && !date.isAfter(endDate);
    }
    
    /**
     * Builds a human-readable warranty certificate.
     * 
     * @return  a formatted certificate string with the warranty's details
     */
    public String generateWarrantyCertificate() {
    return "Certificado de " + getWarrantyType() + "\n"
         + "ID: " + idWarranty + "\n"
         + "Producto: " + product.getTitle() + "\n"
         + "Vigencia: " + startDate + " a " + endDate + "\n"
         + "Costo adicional: $" + getAdditionalCost();
}

    // Each subclass must specify the warranty period for the product (polymorphism)
    public abstract int getDurationInMonths();
    
    // Returns the number of the guarantee type.
    public abstract String getWarrantyType();
    
    // Returns the additional cost that the warranty adds to the sale.
    public abstract Double getAdditionalCost();
   
}

    
