
package com.gamezone.model;

import java.time.LocalDate;

/**
 * Represents a percentage-based promotion in the GameZone system.
 * Applies a global percentage discount to the total amount of a sale.
 * 
 * @author Luis Guerrero
 * @version 1.0
 */
public class PercentageDiscount extends Promotion{
    private double discountPercentage;

    /**
     * Constructs a new PercentageDiscount instance with full details.
     * 
     * @param discountPercentage the percentage discount (between 0 and 100)
     * @param id the unique identifier of the promotion
     * @param name the display name of the promotion
     * @param startDate the start date of validity
     * @param endDate the end date of validity
     */
    public PercentageDiscount(double discountPercentage, String id, String name, LocalDate startDate, LocalDate endDate) {
        super(id, name, startDate, endDate);
        this.discountPercentage = discountPercentage;
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
     * Calculates the discount amount by applying the percentage to the total sale amount.
     * 
     * @param sale the sale transaction to evaluate
     * @return the calculated discount amount in pesos
     */
    @Override
    public double calculateDiscount(Sale sale) {
        if (sale == null) {
            return 0.0;
        }
        return sale.getTotalAmount() * (this.discountPercentage / 100.0);
    }
}
