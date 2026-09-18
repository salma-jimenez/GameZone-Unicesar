
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
    /**
     * Finds a product by its unique identifier.
     *
     * @param productId unique identifier of the product
     * @return Product if found, null otherwise
     */
    public Product getProductById(String productId) {
        return getAllProducts().stream()
                .filter(p -> p.getId().equals(productId))
                .findFirst()
                .orElse(null);
    }

    /**
     * Restores stock for a given product by adding the specified quantity.
     *
     * @param productId unique identifier of the product
     * @param quantity amount to add back to inventory
     */
    public void restoreStock(String productId, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("La cantidad a restaurar debe ser mayor a cero.");
        }
        Product product = getProductById(productId);
        if (product != null) {
            int newQuantity = product.getQuantityAvailable() + quantity;
            updateStock(productId, newQuantity);
        } else {
            throw new IllegalArgumentException("El producto con ID " + productId + " no existe.");
        }
    }
}
