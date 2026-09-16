package com.gamezone.service;

HEAD:src/main/java/com/gamezone/service/SaleService.java

import com.gamezone.model.Customer;
feature/sale-module:src/main/java/com/mycompany/gamezone/service/SaleService.java
import com.gamezone.model.Sale;
import com.gamezone.persistence.SaleRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages business logic and validations for sales.
 */
public class SaleService {

    private final SaleRepository saleRepository;

    public SaleService(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    public Sale registerSale(String idSale, double totalAmount) {
        if (idSale == null || idSale.trim().isEmpty()) {
            throw new IllegalArgumentException("El identificador de la venta no puede estar vacío.");
        }
        if (totalAmount <= 0) {
            throw new IllegalArgumentException("El monto total de la venta debe ser mayor a cero.");
        }
        Customer customer = new Customer(id, name, phone, email);
        Product product = new Product(id, title, price, quantityAvailable);
        List<Product> listproduct = new ArrayList<>();
        listproduct.add(product);
        
        Sale sale = new Sale(idSale, LocalDateTime.now(), totalAmount, customer, listproduct);
        saleRepository.save(sale);
        return sale;
    }

    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }

    public Sale getSaleById(String id) {
        Sale sale = saleRepository.findById(id);
        if (sale == null) {
            throw new IllegalArgumentException("La venta indicada no existe.");
        }
        return sale;
    }
}
