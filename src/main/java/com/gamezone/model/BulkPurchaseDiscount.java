
package com.gamezone.model;

import java.time.LocalDate;

/**
 * Represents a bulk/volume purchase promotion in the GameZone system.
 * Applies a percentage discount to the sale total if the quantity of products 
 * meets or exceeds a specified minimum threshold.
 * 
 * @author Luis Guerrero
 * @version 1.0
 */
public class BulkPurchaseDiscount extends Promotion{
    private int minimumQuantity;
    private double discountPercentage;

    /**
     * Constructs a new BulkPurchaseDiscount instance with full details.
     * 
     * @param minimumQuantity the minimum quantity of products required to qualify
     * @param discountPercentage the percentage discount to apply
     * @param id the unique identifier of the promotion
     * @param name the display name of the promotion
     * @param startDate the start date of validity
     * @param endDate the end date of validity
     */
    public BulkPurchaseDiscount(int minimumQuantity, double discountPercentage, String id, String name, LocalDate startDate, LocalDate endDate) {
        super(id, name, startDate, endDate);
        this.minimumQuantity = minimumQuantity;
        this.discountPercentage = discountPercentage;
    }
    
    /**
     * Gets the minimum quantity of products required.
     * 
     * @return the minimum quantity
     */
    public int getMinimumQuantity() {
        return minimumQuantity;
    }

    /**
     * Sets the minimum quantity of products required.
     * 
     * @param minimumQuantity the new minimum quantity to set
     */
    public void setMinimumQuantity(int minimumQuantity) {
        this.minimumQuantity = minimumQuantity;
    }

    /**
     * Gets the discount percentage.
     * 
     * @return the discount percentage
     */
    public double getDiscountPercentage() {
        return discountPercentage;
    }

    /**
     * Sets the discount percentage.
     * 
     * @param discountPercentage the new discount percentage to set
     */
    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }
    
    /**
     * {@inheritDoc}
     * Calculates the discount amount if the total count of products in the sale 
     * is greater than or equal to the minimum required quantity.
     * 
     * @param sale the sale transaction to evaluate
     * @return the calculated discount amount in pesos, or 0.0 if not eligible
     */
    @Override
    public double calculateDiscount(Sale sale) {
        if (sale == null || sale.getProducts() == null) {
            return 0.0;
        }
        if (sale.getProducts().size() >= this.minimumQuantity) {
            return sale.getTotalAmount() * (this.discountPercentage / 100.0);
        }
        return 0.0;
    }
    
}
