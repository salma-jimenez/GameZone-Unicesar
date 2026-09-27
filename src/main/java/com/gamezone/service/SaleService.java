package com.gamezone.service;

import com.gamezone.model.Accessory;
import com.gamezone.model.Console;
import com.gamezone.model.Product;
import com.gamezone.model.Promotion;
import com.gamezone.model.Sale;
import com.gamezone.persistence.SaleRepository;
import java.util.List;

/**
 * Manages business logic and validations for sales transactions, 
 * including automatic promotion application and warranty assignment.
 */
public class SaleService {
    private final SaleRepository saleRepository;
    private final ProductService productService;
    private final AccessoryService accessoryService;
    private final PromotionService promotionService;
    private final WarrantyService warrantyService; // <- Integrado para Req 4

    public SaleService(SaleRepository saleRepository, ProductService productService, 
                       AccessoryService accessoryService, PromotionService promotionService,
                       WarrantyService warrantyService) {
        this.saleRepository = saleRepository;
        this.productService = productService;
        this.accessoryService = accessoryService;
        this.promotionService = promotionService;
        this.warrantyService = warrantyService;
    }

    /**
     * Registra una venta procesando productos, promociones y garantías.
     */
    public void registerSale(Sale sale, List<String> extendedWarrantyProductIds) {
        if (sale == null) {
            throw new IllegalArgumentException("La venta no puede ser nula.");
        }
        if (sale.getProduct() == null || sale.getProduct().isEmpty()) {
            throw new IllegalArgumentException("Se requiere al menos un producto para registrar la venta.");
        }

        // 1. Validar que haya stock disponible
        for (Product item : sale.getProduct()) {
            if (item.getQuantityAvailable() < 1) {
                throw new IllegalStateException("Stock insuficiente para el producto: " + item.getTitle());
            }
        }

        // 2. Calcular subtotal inicial
        double subtotal = sale.calculateSubtotal();
        sale.setTotalAmount(subtotal);

        // 3. Evaluar y aplicar promociones
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

        // 4. Procesar Garantías (Requerimiento 4)
        double extraWarrantyCost = 0.0;
        if (warrantyService != null) {
            for (Product product : sale.getProduct()) {
                // Garantía Básica automática para consolas
                if (product instanceof Console) {
                    warrantyService.assignBasicWarranty(product, sale, sale.getDateTime().toLocalDate());
                }

                // Garantía Extendida opcional para consolas
                if (extendedWarrantyProductIds != null && extendedWarrantyProductIds.contains(product.getId())) {
                    if (product instanceof Console) {
                        var extendedWarranty = warrantyService.assignExtendedWarranty(product, sale, sale.getDateTime().toLocalDate());
                        extraWarrantyCost += extendedWarranty.getAdditionalCost();
                    }
                }
            }
        }

        // Sumar costo adicional de garantías extendidas al total de la venta
        sale.setTotalAmount(sale.getTotalAmount() + extraWarrantyCost);

        // 5. Descontar inventario
        for (Product item : sale.getProduct()) {
            if (item instanceof Accessory) {
                accessoryService.updateStock(item.getId(), item.getQuantityAvailable() - 1);
            } else {
                productService.updateStock(item.getId(), item.getQuantityAvailable() - 1);
            }
        }

        // 6. Persistir la venta
        saleRepository.save(sale);
    }

    /**
     * Sobrecarga para mantener compatibilidad sin garantías extendidas.
     */
    public void registerSale(Sale sale) {
        registerSale(sale, null);
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