/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gamezone.model;

/**
 *
 * @author Alejandro
 */
public class Console extends Product{
    private String brand;
    private String model;
    private String generation;

    public Console(String brand, String model, String generation, String id, String title, double price, int quantityAvailable) {
        super(id, title, price, quantityAvailable);
        this.brand = brand;
        this.model = model;
        this.generation = generation;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getGeneration() {
        return generation;
    }

    public void setGeneration(String generation) {
        this.generation = generation;
    }

    @Override
    public String getDescription() {
        return ("Consola: "+ this.getTitle()+"\nMarca: "+this.brand + 
                "\nModelo: " + this.model + "\nGeneracion: "+ this.getGeneration()
                + "\nPrecio: "+ this.getPrice());
    }
    
    
}
