
package com.gamezone.model;

import java.time.LocalDate;

/**
 * Represents the extended warranty, optionally added by the seller
 * at the time of sale. Covers factory defects and accidental damage,
 * for 12 months, at a cost of 10% of the product's price.
 * 
 * @author Salomejimenez
 */
public class BasicWarranty extends Warranty {
    
    /**
     * Creates a basic warranty starting on the given date.
     *
     * @param idWarranty the warranty's unique identifier
     * @param product the product under warranty (must be a Console)
     * @param sale the sale associated with this warranty
     * @param startDate the date the warranty starts (usually the sale date)
     */
    public BasicWarranty(String idWarranty, Product product, Sale sale, LocalDate startDate) {
        super(idWarranty, product, sale, startDate);
    }
    
    /**
     * @return the fixed duration of a basic warranty: 6 months
     */
    @Override
    public int getDurationInMonths() {
        return 6;
    }
    
    /**
     * @return the display name of this warranty type
     */
    @Override
    public String getWarrantyType() {
        return "Garantía Básica";
    }
    
     /**
     * @return the additional cost of a basic warranty: always free
     */
    @Override
    public Double getAdditionalCost() {
        return 0.0;
    }
    
}
