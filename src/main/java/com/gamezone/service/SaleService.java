package com.gamezone.service;

import com.gamezone.model.Sale;
import com.gamezone.persistence.SaleRepository;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Manages business logic and validations for sales.
 */
public class SaleService {
    private final SaleRepository saleRepository;

    public SaleService(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    public Sale registerSale(String id, double totalAmount) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("El identificador de la venta no puede estar vacío.");
        }
        if (totalAmount <= 0) {
            throw new IllegalArgumentException("El monto total de la venta debe ser mayor a cero.");
        }
        Sale sale = new Sale(id, LocalDateTime.now(), totalAmount);
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
