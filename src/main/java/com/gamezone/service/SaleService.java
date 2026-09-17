package com.gamezone.service;

import com.gamezone.model.Accessory;
import com.gamezone.model.Product;
import com.gamezone.model.Promotion;
import com.gamezone.model.Sale;
import com.gamezone.persistence.SaleRepository;
import java.util.List;

/**
 * Manages business logic and validations for sales transactions, 
 * including automatic promotion application.
 */
public class SaleService {
    private final SaleRepository saleRepository;
    private final ProductService productService;
    private final AccessoryService accessoryService;
    private final PromotionService promotionService;

    public SaleService(SaleRepository saleRepository, ProductService productService, 
                       AccessoryService accessoryService, PromotionService promotionService) {
        this.saleRepository = saleRepository;
        this.productService = productService;
        this.accessoryService = accessoryService;
        this.promotionService = promotionService;
    }

    public void registerSale(Sale sale) {
        if (sale == null) {
            throw new IllegalArgumentException("La venta no puede ser nula.");
        }
        if (sale.getProduct() == null || sale.getProduct().isEmpty()) {
            throw new IllegalArgumentException("Se requiere al menos un producto para registrar la venta.");
        }

        // 1. Validar que haya stock disponible de cada ítem antes de procesar
        for (Product item : sale.getProduct()) {
            if (item.getQuantityAvailable() < 1) {
                throw new IllegalStateException("Stock insuficiente para el producto: " + item.getTitle());
            }
        }

        // 2. Calcular subtotal inicial
        double subtotal = sale.calculateSubtotal();
        sale.setTotalAmount(subtotal);

        // 3. Evaluar y aplicar automáticamente el mayor descuento
        if (promotionService != null) {
            Promotion bestPromotion = promotionService.findBestPromotionFor(sale);
            if (bestPromotion != null) {
                double discount = bestPromotion.calculateDiscount(sale);
                if (discount > 0) {
                    sale.setAppliedPromotionName(bestPromotion.getName());
                    sale.setDiscountAmount(discount);
                    sale.setTotalAmount(subtotal - discount);
                }
            }
        }

        // 4. Descontar inventario en la capa correspondiente según el tipo de ítem
        for (Product item : sale.getProduct()) {
            if (item instanceof Accessory) {
                accessoryService.updateStock(item.getId(), item.getQuantityAvailable() - 1);
            } else {
                productService.updateStock(item.getId(), item.getQuantityAvailable() - 1);
            }
        }

        // 5. Persistir la venta
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