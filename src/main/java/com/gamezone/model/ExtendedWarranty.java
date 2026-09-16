
package com.gamezone.model;

import java.time.LocalDate;

/**
 *
 * @author Salomejimenez
 */
public class ExtendedWarranty  extends Warranty {
    
     private static double extendedWarrantyRate = 0.10;

    public ExtendedWarranty(String idWarranty, Product product, Sale sale, LocalDate startDate) {
        super(idWarranty, product, sale, startDate);
    }

    @Override
    public int getDurationInMonths() {
        return 12;
    }

    @Override
    public String getWarrantyType() {
        return "Garantía Extendida";
    }

    @Override
    public Double getAdditionalCost() {
        return getProduct().getPrice() * extendedWarrantyRate;
    }
    
    
}
