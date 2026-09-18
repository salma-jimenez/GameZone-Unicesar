
package com.gamezone.model;

import java.time.LocalDate;

/**
 * Represents the base abstract promotion in the GameZone system.
 * Defines common promotion attributes and active date checks, while delegating 
 * the discount calculation to specialized subclasses.
 * 
 * @author Luis Guerrero
 * @version 1.0
 */
public abstract class Promotion{
    private String id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;

    /**
     * Constructs a new Promotion instance with full details.
     * 
     * @param id the unique identifier of the promotion
     * @param name the display name of the promotion
     * @param startDate the start date of validity
     * @param endDate the end date of validity
     */
    public Promotion(String id, String name, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Gets the unique identifier of the promotion.
     * 
     * @return the promotion ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the promotion.
     * 
     * @param id the new promotion ID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the display name of the promotion.
     * 
     * @return the promotion name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the display name of the promotion.
     * 
     * @param name the new promotion name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the start date of validity.
     * 
     * @return the start date
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Sets the start date of validity.
     * 
     * @param startDate the new start date
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets the end date of validity.
     * 
     * @return the end date
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Sets the end date of validity.
     * 
     * @param endDate the new end date
     */
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    
    /**
     * Checks if the specified date falls within the promotion validity range.
     * 
     * @param date the date to check
     * @return true if the date is within range (inclusive), false otherwise
     */
    public boolean isActive(LocalDate date) {
        if (date == null || startDate == null || endDate == null) {
            return false;
        }
        return !date.isBefore(startDate) && !date.isAfter(endDate);
    }
    /**
     * Calculates the monetary discount amount in pesos for the specified sale.
     * 
     * @param sale the sale transaction to evaluate
     * @return the calculated discount amount in pesos
     */
    public abstract double calculateDiscount(Sale sale);
}
