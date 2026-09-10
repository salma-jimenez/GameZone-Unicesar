/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gamezone.service;

import com.mycompany.gamezone.model.Product;
import com.mycompany.gamezone.persistence.ProductRepository;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alejandro
 */
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    
    public void registerProduct(Product product){
        
    }
    
    public List<Product> getAllProducts(){
        List<Product> products = new ArrayList<>();
        
        return products;
    }
    
    public void updateStock(String productId, int quantity){
        
    }
}
