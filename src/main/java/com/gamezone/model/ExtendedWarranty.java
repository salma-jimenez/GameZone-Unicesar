
package com.gamezone.model;

import java.time.LocalDate;

/**
 * 
 * Represents the extended warranty, optionally added by the seller
 * at the time of sale. Covers factory defects and accidental damage,
 * for 12 months, at a cost of 10% of the product's price.
 *
 * @author Salomejimenez
 */
public class ExtendedWarranty  extends Warranty {
    
     private static double extendedWarrantyRate = 0.10;
     
     /**
     * Creates an extended warranty starting on the given date.
     *
     * @param idWarranty the warranty's unique identifier
     * @param product the product under warranty
     * @param sale the sale associated with this warranty
     * @param startDate the date the warranty starts (usually the sale date)
     */

    public ExtendedWarranty(String idWarranty, Product product, Sale sale, LocalDate startDate) {
        super(idWarranty, product, sale, startDate);
    }

    /**
     * @return the fixed duration of an extended warranty: 12 months
     */
    @Override
    public int getDurationInMonths() {
        return 12;
    }
    
    /**
     * @return the display name of this warranty type
     */
    @Override
    public String getWarrantyType() {
        return "Garantía Extendida";
    }
    
    /**
     * @return 10% of the associated product's price
     */
    @Override
    public Double getAdditionalCost() {
        return getProduct().getPrice() * extendedWarrantyRate;
    }
    
    
}
