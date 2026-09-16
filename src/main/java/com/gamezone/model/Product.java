
package com.gamezone.model;
/**
 * Represents the base abstract product in the GameZone store.
 * Contains common properties such as ID, title, price, and available quantity.
 * 
 * @author Luis Guerrero
 * @version 1.0
 */
public abstract class Product {
    
    private String id;
    private String title;
    private double price;
    private int quantityAvailable;
    
    /**
     * Constructs a new Product instance with the specified details.
     * 
     * @param id the unique identifier of the product
     * @param title the title or name of the product
     * @param price the price of the product
     * @param quantityAvailable the initial stock quantity available
     */
    public Product(String id, String title, double price, int quantityAvailable) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.quantityAvailable = quantityAvailable;
    }

    /**
     * Gets the unique identifier of the product.
     * @return the product ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the product.
     * @param id the new unique identifier of the product
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the title or name of the product.
     * @return the product name or title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title or name of the product.
     * @param title title the new title of the product
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the price of the product.
     * @return the product price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price of the product.
     * @param price the new price of the product
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Gets the quantity available of the product.
     * @return the product quantity available
     */
    public int getQuantityAvailable() {
        return quantityAvailable;
    }

    /**
     * Sets the quantity available of the product.
     * @param quantityAvailable the new stock quantity available
     */
    public void setQuantityAvailable(int quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }
    
    /**
     * Generates a formatted string representation with the specific details of the product.
     * @return a string containing the product's detailed description
     */
    public abstract String getDescription();
    
}
