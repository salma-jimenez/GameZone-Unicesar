package com.gamezone.service;

import com.gamezone.model.Accessory;
import com.gamezone.model.Console;
import com.gamezone.model.Product;
import com.gamezone.model.Promotion;
import com.gamezone.model.Sale;
import com.gamezone.persistence.ISaleRepository;
import java.util.List;

/**
 * Manages business logic and validations for sales transactions,
 * including automatic promotion application and warranty assignment.
 * Refactored to adhere to DIP by depending on ISaleRepository abstraction.
 * 
 * @author Salma Jiménez Vega
 */
public class SaleService {
    private final ISaleRepository saleRepository;
    private final ProductService productService;
    private final AccessoryService accessoryService;
    private final PromotionService promotionService;
    private final WarrantyService warrantyService;

    /**
     * Constructs a SaleService with the required repository and service dependencies.
     * 
     * @param saleRepository the sale repository abstraction
     * @param productService the product service
     * @param accessoryService the accessory service
     * @param promotionService the promotion service
     * @param warrantyService the warranty service
     */
    public SaleService(ISaleRepository saleRepository, ProductService productService, 
                       AccessoryService accessoryService, PromotionService promotionService, 
                       WarrantyService warrantyService) {
        this.saleRepository = saleRepository;
        this.productService = productService;
        this.accessoryService = accessoryService;
        this.promotionService = promotionService;
        this.warrantyService = warrantyService;
    }

    /**
     * Registers a sale transaction processing products, promotions, and warranties.
     * 
     * @param sale the sale to register
     * @param extendedWarrantyProductIds list of product IDs requesting extended warranty
     */
    public void registerSale(Sale sale, List<String> extendedWarrantyProductIds) {
        if (sale == null) {
            throw new IllegalArgumentException("La venta no puede ser nula.");
        }
        if (sale.getProduct() == null || sale.getProduct().isEmpty()) {
            throw new IllegalArgumentException("Se requiere al menos un producto para registrar la venta.");
        }

        // 1. Validate available stock
        for (Product item : sale.getProduct()) {
            if (item.getQuantityAvailable() < 1) {
                throw new IllegalStateException("Stock insuficiente para el producto: " + item.getTitle());
            }
        }

        // 2. Calculate initial subtotal
        double subtotal = sale.calculateSubtotal();
        sale.setTotalAmount(subtotal);

        // 3. Evaluate and apply promotions
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

        // 4. Process Warranties
        double extraWarrantyCost = 0.0;
        if (warrantyService != null) {
            for (Product product : sale.getProduct()) {
                if (product instanceof Console) {
                    warrantyService.assignBasicWarranty(product, sale, sale.getDateTime().toLocalDate());
                }
                if (extendedWarrantyProductIds != null && extendedWarrantyProductIds.contains(product.getId())) {
                    if (product instanceof Console) {
                        var extendedWarranty = warrantyService.assignExtendedWarranty(product, sale, sale.getDateTime().toLocalDate());
                        extraWarrantyCost += extendedWarranty.getAdditionalCost();
                    }
                }
            }
        }

        sale.setTotalAmount(sale.getTotalAmount() + extraWarrantyCost);

        // 5. Update inventory stock
        for (Product item : sale.getProduct()) {
            if (item instanceof Accessory) {
                accessoryService.updateStock(item.getId(), item.getQuantityAvailable() - 1);
            } else {
                productService.updateStock(item.getId(), item.getQuantityAvailable() - 1);
            }
        }

        // 6. Persist sale
        saleRepository.save(sale);
    }

    /**
     * Overload to register a sale without extended warranties.
     * 
     * @param sale the sale to register
     */
    public void registerSale(Sale sale) {
        registerSale(sale, null);
    }

    /**
     * Retrieves all recorded sales.
     * 
     * @return list of sales
     */
    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }

    /**
     * Finds a sale by its unique identifier.
     * 
     * @param id the sale ID
     * @return the sale found
     */
    public Sale getSaleById(String id) {
        Sale sale = saleRepository.findById(id);
        if (sale == null) {
            throw new IllegalArgumentException("La venta indicada no existe.");
        }
        return sale;
    }
}