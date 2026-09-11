
package com.gamezone.model;

import java.time.LocalDateTime;

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
    private LocalDateTime LocalDateStart;
    private LocalDateTime LocalDateEnd;

    /**
     *
     * @param idWarranty the warranty's unique identifier
     * @param product product under warranty 
     * @param sale purchase of the product
     * @param LocalDateStart the system's local time to determine the exact time of purchase
     */
    public Warranty(String idWarranty, Product product, Sale sale, LocalDateTime LocalDateStart, LocalDateTime LocalDateEnd) {    
        this.idWarranty = idWarranty;
        this.product = product;
        this.sale = sale;
        this.LocalDateStart = LocalDateStart;
        this.LocalDateEnd = LocalDateEnd;
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
     * @return LocalDateStart
     */

    public LocalDateTime getLocalDateStart() {
        return LocalDateStart;
    }

    /**
     * Sets system local time 
     *
     * @param LocalDateStart
     */

    public void setLocalDateStart(LocalDateTime LocalDateStart) {
        this.LocalDateStart = LocalDateStart;
    }

    /**
     * system local time 
     *
     * @return LocalDateEnd
     */

    public LocalDateTime getLocalDateEnd() {
        return LocalDateEnd;
    }

    /**
     * sets system local time 
     *
     * @param LocalDateEnd
     */

    public void setLocalDateEnd(LocalDateTime LocalDateEnd) {
        this.LocalDateEnd = LocalDateEnd;
    }

    // Each subclass must specify the warranty period for the product (polymorphism)
    public abstract int getDurationInMonths();
    
    // Returns the number of the guarantee type.
    public abstract String getWarrantyType();
    
    // Returns the additional cost that the warranty adds to the sale.
    public abstract Double getAdditionalCost();
   
}

    
