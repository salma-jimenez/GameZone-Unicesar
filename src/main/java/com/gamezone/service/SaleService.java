package com.gamezone.service;

import com.gamezone.model.Accessory;
import com.gamezone.model.Console;
import com.gamezone.model.Product;
import com.gamezone.model.Promotion;
import com.gamezone.model.Sale;
import com.gamezone.persistence.SaleRepository;
import java.time.LocalDateTime;
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
    
    public Sale createSale(String id, List<Product> products) {
        return new Sale(id, LocalDateTime.now(), 0.0, null, products);
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

        // =========================================================================
        // 1. EJEMPLO EXPOSICIÓN: INFORMATION EXPERT (EXPERTO EN INFORMACIÓN)
        // =========================================================================
        
        /* 
        // [ANTES - VIOLA EL PATRÓN]
        // SaleService inspeccionaba los productos uno a uno con un "instanceof"
        // para validar el stock por su cuenta, asumiendo datos ajenos.
        
        for (Product item : sale.getProduct()) {
            if (item instanceof Accessory) {
                if (((Accessory) item).getQuantityAvailable() < 1) {
                    throw new IllegalStateException("Stock insuficiente para el accesorio: " + item.getTitle());
                }
            } else {
                if (item.getQuantityAvailable() < 1) {
                    throw new IllegalStateException("Stock insuficiente para el producto: " + item.getTitle());
                }
            }
        }
        */

        // [DESPUÉS - APLICA INFORMATION EXPERT]
        // Delegamos la validación a 'Sale', ya que ella posee la lista de productos,
        // y a su vez cada producto valida su propio stock.
        sale.validateStock();

        // =========================================================================
        // FIN EJEMPLO 1
        // =========================================================================
        
        // =========================================================================
        // 5. EJEMPLO EXPOSICIÓN: HIGH COHESION (ALTA COHESIÓN)
        // =========================================================================
        
        /* 
        // [ANTES - VIOLA EL PATRÓN]
        // Un método gigante y monolítico que hacía 8 tareas diferentes mezcladas
        // (calcular subtotales, buscar promos, asignar garantías, tocar inventario, etc.).
        */

        // [DESPUÉS - APLICA HIGH COHESION]
        // El proceso principal se divide en métodos privados enfocados y altamente cohesivos.
        
        double subtotal = sale.calculateSubtotal();
        sale.setTotalAmount(subtotal);
        
        applyBestPromotion(sale);                                // Tarea específica 1
        
        double extraWarrantyCost = assignWarranties(sale, extendedWarrantyProductIds); // Tarea específica 2
        
        double discount = sale.getDiscountAmount();
        double finalTotal = subtotal - discount + extraWarrantyCost;
        sale.setTotalAmount(finalTotal);                         
        
        discountStock(sale);                                     // Tarea específica 3
        saleRepository.save(sale);                               // Persistencia

        // =========================================================================
        // FIN EJEMPLO 5
        // =========================================================================
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
    
    // --- MÉTODOS AUXILIARES (APOYAN LA ALTA COHESIÓN) ---
    private void applyBestPromotion(Sale sale) {
        if (promotionService != null) {
            Promotion bestPromotion = promotionService.findBestPromotionFor(sale);
            if (bestPromotion != null) {
                double discount = bestPromotion.calculateDiscount(sale);
                if (discount > 0) {
                    sale.setAppliedPromotionName(bestPromotion.getName());
                    sale.setDiscountAmount(discount);
                }
            }
        }
    }

    private double assignWarranties(Sale sale, List<String> extendedIds) {
        double extraCost = 0.0;
        if (warrantyService != null) {
            for (Product product : sale.getProduct()) {
                if (product instanceof Console) {
                    warrantyService.assignBasicWarranty(product, sale, sale.getDateTime().toLocalDate());
                }
                if (extendedIds != null && extendedIds.contains(product.getId())) {
                    if (product instanceof Console) {
                        var ext = warrantyService.assignExtendedWarranty(product, sale, sale.getDateTime().toLocalDate());
                        extraCost += ext.getAdditionalCost();
                    }
                }
            }
        }
        return extraCost;
    }

    private void discountStock(Sale sale) {
        for (Product item : sale.getProduct()) {
            if (item instanceof Accessory) {
                accessoryService.updateStock(item.getId(), item.getQuantityAvailable() - 1);
            } else {
                productService.updateStock(item.getId(), item.getQuantityAvailable() - 1);
            }
        }
    }
}