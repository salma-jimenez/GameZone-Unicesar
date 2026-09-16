
package com.gamezone.service;

import com.gamezone.model.Product;
import com.gamezone.persistence.ProductRepository;
import java.util.List;

/**
 * Manages the store's business logic and acts as an intermediary between the interface and ProductRepository
 * 
 * @author Luis Guerrero
 * @version 1.0
 */
public class ProductService {
    private final ProductRepository productRepository;

    /**
     * Injects the productRepository parameter.
     * @param productRepository the repository used for product data persistence
     */
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    
    /**
     * Registers the entered product.
     * @param product the product to be registered
     */
    public void registerProduct(Product product){
        List<Product> products = productRepository.load();
        products.add(product);
        productRepository.save(products);
    }
    
    /**
     * Gets the list of all products.
     * @return the product list
     */
    public List<Product> getAllProducts(){
        List<Product> products = productRepository.load();
        return products;
    }
    
    /**
     * Updates the stock quantity of a specific product.
     * @param productId the unique identifier of the product used to search for the existing product whose stock is to be updated.
     * @param quantity the stock quantity to be updated
     */
    public void updateStock(String productId, int quantity){
        List<Product> products = productRepository.load();
        for (Product p : products){
            if(p.getId().equals(productId)){
                p.setQuantityAvailable(quantity);
                break;
            }
        }
        productRepository.save(products);
    }
}
