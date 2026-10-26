
package com.gamezone.validation;

import com.gamezone.exceptions.BusinessRuleException;
import com.gamezone.exceptions.InvalidDataException;
import com.gamezone.exceptions.ResourceNotFoundException;
import com.gamezone.model.Product;

/**
 * Validator class for Product-related operations.
 * Centralizes business rules and data format validations for the product module.
 */
public class ProductValidator {

    /**
     * Validates the basic data for a product.
     * 
     * @param id the product identifier
     * @param title the product title
     * @param price the product price
     * @param stock the product stock quantity
     */
    public static void validateProductData(String id, String title, double price, int stock) {
        if (id == null || id.trim().isEmpty()) {
            throw new InvalidDataException("id", "El identificador no puede ser nulo ni estar vacío.");
        }
        if (title == null || title.trim().isEmpty()) {
            throw new InvalidDataException("title", "El título no puede ser nulo ni estar vacío.");
        }
        if (price <= 0) {
            throw new InvalidDataException("price", "El precio debe ser un valor positivo mayor a cero.");
        }
        if (stock < 0) {
            throw new InvalidDataException("stock", "El stock no puede ser un valor negativo.");
        }
    }

    /**
     * Validates that a product has sufficient stock to fulfill a request.
     * 
     * @param product the product being checked
     * @param requestedQuantity the quantity requested by the user
     */
    public static void validateStockAvailability(Product product, int requestedQuantity) {
        if (product.getQuantityAvailable() < requestedQuantity) {
            throw new BusinessRuleException("Stock insuficiente para el producto. Stock actual: " 
                    + product.getQuantityAvailable() + ", Solicitado: " + requestedQuantity);
        }
    }

    /**
     * Validates that a product object exists (is not null).
     * 
     * @param product the product instance returned by the repository
     * @param productId the identifier used to search for the product
     */
    public static void validateProductExists(Product product, String productId) {
        if (product == null) {
            throw new ResourceNotFoundException("producto", productId);
        }
    }
}