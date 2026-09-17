
package com.gamezone.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an abstract accessory product in the GameZone store.
 * Extends the abstract {@link Product} class and manages compatibility with 
 * various consoles.
 * 
 * @author Luis Guerrero
 * @version 1.0
 */
public abstract class Accessory extends Product{
    private List<String> compatibleConsoles;

    /**
     * Constructs a new Accessory instance with full product details and compatibility list.
     * 
     * @param compatibleConsoles compatibleConsoles list of compatible console names or models
     * @param id id the unique identifier of the product
     * @param title title the title or name of the accessory
     * @param price price the price of the accessory
     * @param quantityAvailable quantityAvailable the available stock quantity
     */
    public Accessory(List compatibleConsoles, String id, String title, double price, int quantityAvailable) {
        super(id, title, price, quantityAvailable);
        this.compatibleConsoles = compatibleConsoles;
    }
    
    public List<String> getCompatibleConsoles(){
        return compatibleConsoles;
    }
    
    public void setCompatibleConsoles(List<String> compatibleConsoles){
        this.compatibleConsoles = compatibleConsoles;
    }
    
    public void addCompatibleConsole(String console) {
        if (this.compatibleConsoles == null) {
            this.compatibleConsoles = new ArrayList<>();
        }
        if (console != null && !console.isBlank()) {
            this.compatibleConsoles.add(console);
        }
    }
    
    public boolean isCompatibleWith(String console) {
        if (compatibleConsoles == null || console == null) {
            return false;
        }
        for (String c : compatibleConsoles) {
            if (c.equalsIgnoreCase(console)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getDescription() {
        String consoles = (compatibleConsoles == null || compatibleConsoles.isEmpty()) 
                ? "Ninguna" 
                : String.join(", ", compatibleConsoles);
                
        return "Accesorio: " + this.getTitle() + "\n"
             + "Precio: $" + this.getPrice() + "\n"
             + "Cantidad disponible: " + this.getQuantityAvailable() + "\n"
             + "Consolas compatibles: " + consoles;
    }

}
