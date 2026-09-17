package com.gamezone.service;

import com.gamezone.model.Accessory;
import com.gamezone.model.Product;
import com.gamezone.model.Sale;
import com.gamezone.persistence.SaleRepository;
import java.util.List;

/**
 * Manages business logic and validations for sales transactions.
 */
public class SaleService {
    private final SaleRepository saleRepository;
    private final ProductService productService;
    private final AccessoryService accessoryService;

    public SaleService(SaleRepository saleRepository, ProductService productService, AccessoryService accessoryService) {
        this.saleRepository = saleRepository;
        this.productService = productService;
        this.accessoryService = accessoryService;
    }

    public void registerSale(Sale sale) {
        if (sale == null) {
            throw new IllegalArgumentException("La venta no puede ser nula.");
        }
        if (sale.getProduct() == null || sale.getProduct().isEmpty()) {
            throw new IllegalArgumentException("Se requiere al menos un producto para registrar la venta.");
        }

        // Validar que haya stock disponible de cada ítem antes de procesar
        for (Product item : sale.getProduct()) {
            if (item.getQuantityAvailable() < 1) {
                throw new IllegalStateException("Stock insuficiente para el producto: " + item.getTitle());
            }
        }

        // Descontar inventario en la capa correspondiente según el tipo de ítem
        for (Product item : sale.getProduct()) {
            if (item instanceof Accessory) {
                accessoryService.updateStock(item.getId(), item.getQuantityAvailable() - 1);
            } else {
                productService.updateStock(item.getId(), item.getQuantityAvailable() - 1);
            }
        }

        saleRepository.save(sale);
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