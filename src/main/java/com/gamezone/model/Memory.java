
package com.gamezone.model;

import java.util.List;

/**
 * Represents a memory accessory in the GameZone system.
 * Extends {@link Accessory} by adding capacity and memory type properties.
 * 
 * @author Luis Guerrero
 * @version 1.0
 */
public class Memory extends Accessory{
    private int capacityInGB;
    private String memoryType;

    /**
     * Constructs a new Memory instance with full product details, capacity, and memory type.
     * 
     * @param capacityInGB the storage capacity in gigabytes
     * @param memoryType the type of memory
     * @param compatibleConsoles list of compatible console names or models
     * @param id the unique identifier of the product
     * @param title the title or name of the memory accessory
     * @param price the price of the memory accessory
     * @param quantityAvailable the available stock quantity
     */
    public Memory(int capacityInGB, String memoryType, List compatibleConsoles, String id, String title, double price, int quantityAvailable) {
        super(compatibleConsoles, id, title, price, quantityAvailable);
        this.capacityInGB = capacityInGB;
        this.memoryType = memoryType;
    }

    /**
     * Gets the storage capacity in gigabytes.
     * 
     * @return the capacity in GB
     */
    public int getCapacityInGB() {
        return capacityInGB;
    }

    /**
     * Sets the storage capacity in gigabytes.
     * 
     * @param capacityInGB the new capacity in GB to set
     */
    public void setCapacityInGB(int capacityInGB) {
        this.capacityInGB = capacityInGB;
    }

    /**
     * Gets the memory type.
     * 
     * @return the memory type
     */
    public String getMemoryType() {
        return memoryType;
    }

    /**
     * Sets the memory type.
     * 
     * @param memoryType the new memory type to set
     */
    public void setMemoryType(String memoryType) {
        this.memoryType = memoryType;
    }
    
    /**
     * {@inheritDoc}
     * Returns a formatted summary including memory specific details.
     * 
     * @return a formatted string with memory details
     */
    @Override
    public String getDescription() {
        return super.getDescription() + "\n"
             + "Capacidad: " + this.capacityInGB + " GB\n"
             + "Tipo de memoria: " + this.memoryType;
    }
    
}
