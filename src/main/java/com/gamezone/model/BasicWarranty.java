
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
    
    public BasicWarranty(String idWarranty, Product product, Sale sale, LocalDate startDate) {
        super(idWarranty, product, sale, startDate);
    }

    @Override
    public int getDurationInMonths() {
        return 6;
    }

    @Override
    public String getWarrantyType() {
        return "Garantía Básica";
    }

    @Override
    public Double getAdditionalCost() {
        return 0.0;
    }
    
}
