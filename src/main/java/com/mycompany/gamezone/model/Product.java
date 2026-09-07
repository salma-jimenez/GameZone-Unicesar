
package com.mycompany.gamezone.model;

public class Product {
    private String id;
    private String tittle;
    private double price;
    private int quantityAvailable;

    public Product(String id, String tittle, double price, int quantityAvailable) {
        this.id = id;
        this.tittle = tittle;
        this.price = price;
        this.quantityAvailable = quantityAvailable;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
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
