
package com.gamezone.model;

/**
 * Represents a console product in the GameZone store.
 * Extends the abstract {@link Product} class by adding brand, model, and generation properties.
 * 
 * @author Alejandro
 * @version 1.0
 */
public class Console extends Product{
    private String brand;
    private String model;
    private String generation;

    /**
     * Constructs a new Console instance with full product details.
     * @param brand the console brand
     * @param model the console model
     * @param generation the console generation
     * @param id the unique identifier of the product
     * @param title the title of the video game
     * @param price the price of the video game
     * @param quantityAvailable the available stock quantity
     */
    public Console(String brand, String model, String generation, String id, String title, double price, int quantityAvailable) {
        super(id, title, price, quantityAvailable);
        this.brand = brand;
        this.model = model;
        this.generation = generation;
    }

    /**
     * Gets the console brand.
     * @return the brand of the console
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Sets the console brand.
     * @param brand the new brand of the console
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * Gets the console model.
     * @return the model of the console
     */
    public String getModel() {
        return model;
    }

    /**
     * Sets the console model.
     * @param model the new model of the console
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * Gets the console generation.
     * @return the generation of the console
     */
    public String getGeneration() {
        return generation;
    }

    /**
     * Sets the console generation.
     * @param generation the new generation of the console
     */
    public void setGeneration(String generation) {
        this.generation = generation;
    }

    /**
     * {@inheritDoc}
     * Returns a formatted summary including the console details.
     * 
     * @return a formatted string with console details
     */
    @Override
    public String getDescription() {
        return ("Consola: "+ this.getTitle()+"\nMarca: "+this.brand + 
                "\nModelo: " + this.model + "\nGeneracion: "+ this.getGeneration()
                + "\nPrecio: "+ this.getPrice());
    }
    
    
}
