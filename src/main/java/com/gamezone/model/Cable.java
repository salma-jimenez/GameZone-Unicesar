
package com.gamezone.model;

import java.util.List;

/**
 * Represents a cable accessory in the GameZone system.
 * Extends {@link Accessory} by adding length and connector type properties.
 * 
 * @author Luis Guerrero
 * @version 1.0
 */
public class Cable extends Accessory{
    private double lengthInMeters;
    private String connectorType;

    /**
     * Constructs a new Cable instance with full product details, length, and connector type.
     * 
     * @param lengthInMeters the length of the cable in meters
     * @param connectorType the type of connector
     * @param compatibleConsoles list of compatible console names or models
     * @param id the unique identifier of the product
     * @param title the title or name of the cable
     * @param price the price of the cable
     * @param quantityAvailable the available stock quantity
     */
    public Cable(double lengthInMeters, String connectorType, List compatibleConsoles, String id, String title, double price, int quantityAvailable) {
        super(compatibleConsoles, id, title, price, quantityAvailable);
        this.lengthInMeters = lengthInMeters;
        this.connectorType = connectorType;
    }

    /**
     * Gets the length of the cable in meters.
     * 
     * @return the cable length
     */
    public double getLengthInMeters() {
        return lengthInMeters;
    }

    /**
     * Sets the length of the cable in meters.
     * 
     * @param lengthInMeters the new cable length to set
     */
    public void setLengthInMeters(double lengthInMeters) {
        this.lengthInMeters = lengthInMeters;
    }

    /**
     * Gets the connector type of the cable.
     * 
     * @return the connector type
     */
    public String getConnectorType() {
        return connectorType;
    }

    /**
     * Sets the connector type of the cable.
     * 
     * @param connectorType the new connector type to set
     */
    public void setConnectorType(String connectorType) {
        this.connectorType = connectorType;
    }
    
    /**
     * {@inheritDoc}
     * Returns a formatted summary including cable specific details.
     * 
     * @return a formatted string with cable details
     */
    @Override
    public String getDescription() {
        return super.getDescription() + "\n"
             + "Longitud: " + this.lengthInMeters + " m\n"
             + "Tipo de conector: " + this.connectorType;
    }
}
