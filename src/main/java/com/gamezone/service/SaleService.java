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

        // =====================================================================
        // INICIO EJEMPLO EXPOSICIÓN: INFORMATION EXPERT (EXPERTO EN INFORMACIÓN)
        // =====================================================================
        
        // 1 & 2. Validar stock de productos y accesorios antes de procesar
        
        /* 
        // [ANTES - VIOLA EL PRINCIPIO]
        // SaleService asume la responsabilidad de revisar el stock de cada producto,
        // inspeccionando sus datos internos e incluso rompiendo la encapsulación 
        // y el polimorfismo al verificar "instanceof".
        
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

        // [DESPUÉS - APLICA EL PRINCIPIO]
        // La entidad 'Sale' es la experta en conocer su lista de productos, y a su vez,
        // cada 'Product' es experto en conocer su propio stock. El servicio solo delega.
        
        sale.validateStock();

        // =====================================================================
        // FIN EJEMPLO EXPOSICIÓN
        // =====================================================================
        
        // 3. Crear la venta y calcular el subtotal base de los ítems
        double subtotal = sale.calculateSubtotal();
        sale.setTotalAmount(subtotal); // O un método específico para subtotal si lo prefieres

        // 4. Consultar PromotionService.findBestPromotionFor(sale) y calcular el descuento solo sobre el subtotal
        double discount = 0.0;
        if (promotionService != null) {
            Promotion bestPromotion = promotionService.findBestPromotionFor(sale);
            if (bestPromotion != null) {
                discount = bestPromotion.calculateDiscount(sale);
                if (discount > 0) {
                    sale.setAppliedPromotionName(bestPromotion.getName());
                    sale.setDiscountAmount(discount);
                }
            }
        }

        // 5. Generar la garantía básica de cada consola y las garantías extendidas solicitadas, sumando su costo
        double extraWarrantyCost = 0.0;
        if (warrantyService != null) {
            for (Product product : sale.getProduct()) {
                if (product instanceof Console) {
                    // Garantía Básica automática (costo cero)
                    warrantyService.assignBasicWarranty(product, sale, sale.getDateTime().toLocalDate());
                }

                // Si se solicitó la garantía extendida para esta consola
                if (extendedWarrantyProductIds != null && extendedWarrantyProductIds.contains(product.getId())) {
                    if (product instanceof Console) {
                        var extendedWarranty = warrantyService.assignExtendedWarranty(product, sale, sale.getDateTime().toLocalDate());
                        extraWarrantyCost += extendedWarranty.getAdditionalCost();
                    }
                }
            }
        }

        // 6. Calcular el total final: subtotal - descuento + costo de garantías extendidas
        double finalTotal = subtotal - discount + extraWarrantyCost;
        sale.setTotalAmount(finalTotal);

        // 7. Actualizar el inventario delegando en ProductService o AccessoryService según el tipo del ítem
        for (Product item : sale.getProduct()) {
            if (item instanceof Accessory) {
                accessoryService.updateStock(item.getId(), item.getQuantityAvailable() - 1);
            } else {
                productService.updateStock(item.getId(), item.getQuantityAvailable() - 1);
            }
        }

        // 8. Persistir la venta
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