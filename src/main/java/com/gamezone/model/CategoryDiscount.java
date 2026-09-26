
package com.gamezone.model;

import java.time.LocalDate;

/**
 * Represents a category-specific promotion in the GameZone system.
 * Applies a percentage discount exclusively to products belonging to a target category.
 * 
 * @author Luis Guerrero
 * @version 1.0
 */
public class CategoryDiscount extends Promotion{
    private double discountPercentage;
    private String targetCategory;

    /**
     * Constructs a new CategoryDiscount instance with full details.
     * 
     * @param discountPercentage the percentage discount to apply
     * @param targetCategory the target category ("VIDEOGAME" or "CONSOLE")
     * @param id the unique identifier of the promotion
     * @param name the display name of the promotion
     * @param startDate the start date of validity
     * @param endDate the end date of validity
     */
    public CategoryDiscount(double discountPercentage, String targetCategory, String id, String name, LocalDate startDate, LocalDate endDate) {
        super(id, name, startDate, endDate);
        this.discountPercentage = discountPercentage;
        this.targetCategory = targetCategory;
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
     * Gets the target category for the discount.
     * 
     * @return the target category string
     */
    public String getTargetCategory() {
        return targetCategory;
    }

    /**
     * Sets the target category for the discount.
     * 
     * @param targetCategory the new target category ("VIDEOGAME", "CONSOLE", or "ACCESSORY")
     */
    public void setTargetCategory(String targetCategory) {
        this.targetCategory = targetCategory;
    }

    /**
     * {@inheritDoc}
     * Calculates the discount amount by applying the percentage only to products 
     * matching the target category.
     * 
     * @param sale the sale transaction to evaluate
     * @return the calculated discount amount in pesos
     */
    @Override
    public double calculateDiscount(Sale sale) {
        if (sale == null || sale.getProducts() == null) {
            return 0.0;
        }

        double categoryTotal = 0.0;

        for (Product product : sale.getProducts()) {
            if ("VIDEOGAME".equalsIgnoreCase(targetCategory) && product instanceof VideoGame) {
                categoryTotal += product.getPrice();
            } else if ("CONSOLE".equalsIgnoreCase(targetCategory) && product instanceof Console) {
                categoryTotal += product.getPrice();
            } else if ("ACCESSORY".equalsIgnoreCase(targetCategory) && product instanceof Accessory){
                categoryTotal += product.getPrice();
            }
        }

        return categoryTotal * (this.discountPercentage / 100.0);
    }
}
