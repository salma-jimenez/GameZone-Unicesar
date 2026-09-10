
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
        List<Product> products = productRepository.load();
        products.add(product);
        productRepository.save(products);
    }
    
    public List<Product> getAllProducts(){
        List<Product> products = productRepository.load();
        return products;
    }
    
    public void updateStock(String productId, int quantity){
        
    }
}
