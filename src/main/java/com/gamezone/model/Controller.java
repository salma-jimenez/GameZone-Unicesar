
package com.gamezone.model;

import java.util.List;

/**
 * Represents a controller accessory in the GameZone system.
 * Extends {@link Accessory} by adding a specific connection type.
 * 
 * @author Luis Guerrero
 * @version 1.0
 */
public class Controller extends Accessory{
    private String connectionType;

    /**
     * Constructs a new Controller instance with full product details and connection type.
     * 
     * @param connectionType the connection type
     * @param compatibleConsoles list of compatible console names or models
     * @param id the unique identifier of the product
     * @param title the title or name of the controller
     * @param price the price of the controller
     * @param quantityAvailable the available stock quantity
     */
    public Controller(String connectionType, List compatibleConsoles, String id, String title, double price, int quantityAvailable) {
        super(compatibleConsoles, id, title, price, quantityAvailable);
        this.connectionType = connectionType;
    }

    /**
     * Gets the connection type of the controller.
     * 
     * @return the connection type
     */
    public String getConnectionType() {
        return connectionType;
    }

    /**
     * Sets the connection type of the controller.
     * 
     * @param connectionType the new connection type to set
     */
    public void setConnectionType(String connectionType) {
        this.connectionType = connectionType;
    }
    
    /**
     * {@inheritDoc}
     * Returns a formatted summary including controller specific details.
     * 
     * @return a formatted string with controller details
     */
    @Override
    public String getDescription() {
        return super.getDescription() + "\n"
             + "Tipo de conexión: " + this.connectionType;
    }
    
}
