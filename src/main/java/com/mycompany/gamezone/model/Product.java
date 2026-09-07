
package com.mycompany.gamezone.model;

public abstract class Product {
    private String id;
    private String title;
    private double price;
    private int quantityAvailable;

    public Product(String id, String title, double price, int quantityAvailable) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.quantityAvailable = quantityAvailable;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(int quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }
    
    public abstract String getDescription();
    
}
