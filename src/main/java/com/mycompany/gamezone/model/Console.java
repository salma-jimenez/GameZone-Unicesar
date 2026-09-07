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
    private String generarion;

    public Console(String brand, String model, String generarion, String id, String title, double price, int quantityAvailable) {
        super(id, title, price, quantityAvailable);
        this.brand = brand;
        this.model = model;
        this.generarion = generarion;
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

    public String getGenerarion() {
        return generarion;
    }

    public void setGenerarion(String generarion) {
        this.generarion = generarion;
    }

    @Override
    public String getDescription() {
        return ("Consola: "+ this.getTitle()+"\nMarca: "+this.brand + 
                "\nModelo: " + this.model + "\nGeneracion: "+ this.getGenerarion()
                + "\nPrecio: "+ this.getPrice());
    }
    
    
}
