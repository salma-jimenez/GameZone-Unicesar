
package com.gamezone.validation;

import com.gamezone.exceptions.BusinessRuleException;
import com.gamezone.exceptions.InvalidDataException;
import com.gamezone.exceptions.ResourceNotFoundException;
import com.gamezone.model.Customer;
import com.gamezone.model.Product;
import com.gamezone.model.Sale;
import com.gamezone.model.Seller;
import java.util.List;

/**
 * Validator class for Sale-related operations.
 * Centralizes business rules and data format validations for the sale module.
 */
public class SaleValidator {

    /**
     * Validates the data required to process a sale.
     * 
     * @param customer the customer making the purchase
     * @param seller the seller processing the sale
     * @param products the list of products being sold
     */
    public static void validateSaleData(Customer customer, Seller seller, List<Product> products) {
        if (customer == null) {
            throw new InvalidDataException("cliente", "El cliente de la venta no puede ser nulo.");
        }
        if (seller == null) {
            throw new InvalidDataException("vendedor", "El vendedor de la venta no puede ser nulo.");
        }
        if (products == null || products.isEmpty()) {
            throw new BusinessRuleException("No se puede registrar una venta sin productos. La lista está vacía.");
        }
    }

    /**
     * Validates that a sale object exists (is not null).
     * 
     * @param sale the sale instance returned by the repository
     * @param saleId the identifier used to search for the sale
     */
    public static void validateSaleExists(Sale sale, String saleId) {
        if (sale == null) {
            throw new ResourceNotFoundException("venta", saleId);
        }
    }
}