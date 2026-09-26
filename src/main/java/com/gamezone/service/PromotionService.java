
package com.gamezone.service;
import com.gamezone.model.BulkPurchaseDiscount;
import com.gamezone.model.CategoryDiscount;
import com.gamezone.model.PercentageDiscount;
import com.gamezone.model.Promotion;
import com.gamezone.model.Sale;
import com.gamezone.persistence.PromotionRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Coordinates the promotion rules of the store: registering new
 * discounts, listing them, and picking out the one that benefits a
 * given sale the most.
 *
 * @author Salomejimenez
 */
public class PromotionService {
    
    private PromotionRepository promotionRepository;

    /**
     * Wires this service to the repository it will use to read and
     * write promotion records.
     *
     * @param promotionRepository the persistence layer for promotions
     */
    public PromotionService(PromotionRepository promotionRepository) {
        this.promotionRepository = promotionRepository;
    }

    /**
     * Adds a new store-wide percentage discount to the catalog of
     * promotions and saves it alongside the ones already registered.
     *
     * @param id unique identifier for the new promotion
     * @param name display name shown to the seller/customer
     * @param startDate first day the promotion is valid
     * @param endDate last day the promotion is valid
     * @param discountPercentage the percentage applied to the sale total
     * @return the promotion that was just created
     */
    public PercentageDiscount registerPercentageDiscount(String id, String name, LocalDate startDate,
                                                          LocalDate endDate, double discountPercentage) {
        PercentageDiscount promotion = new PercentageDiscount(discountPercentage, id, name, startDate, endDate);
        List<Promotion> promotions = promotionRepository.loadAll();
        promotions.add(promotion);
        promotionRepository.saveAll(promotions);
        return promotion;
    }

    /**
     * Registers a promotion that only discounts products of a single
     * category, such as every video game, every console, or every accessory in the sale.
     *
     * @param id unique identifier for the new promotion
     * @param name display name shown to the seller/customer
     * @param startDate first day the promotion is valid
     * @param endDate last day the promotion is valid
     * @param discountPercentage the percentage applied within that category
     * @param targetCategory the category this promotion targets ("VIDEOGAME", "CONSOLE", or "ACCESSORY")
     * @return the promotion that was just created
     */
    public CategoryDiscount registerCategoryDiscount(String id, String name, LocalDate startDate, LocalDate endDate,
                                                       double discountPercentage, String targetCategory) {
        if (targetCategory == null || (!targetCategory.equalsIgnoreCase("VIDEOGAME") && 
             !targetCategory.equalsIgnoreCase("CONSOLE") && 
             !targetCategory.equalsIgnoreCase("ACCESSORY"))) {
            throw new IllegalArgumentException("Error, la categoría objetivo debe ser VIDEOGAME, CONSOLE o ACCESSORY");
        }
        CategoryDiscount promotion = new CategoryDiscount(discountPercentage, targetCategory, id, name, startDate, endDate);
        List<Promotion> promotions = promotionRepository.loadAll();
        promotions.add(promotion);
        promotionRepository.saveAll(promotions);
        return promotion;
    }

    /**
     * Sets up a volume-based promotion that rewards customers who buy
     * at least a minimum number of products in a single sale.
     *
     * @param id unique identifier for the new promotion
     * @param name display name shown to the seller/customer
     * @param startDate first day the promotion is valid
     * @param endDate last day the promotion is valid
     * @param minimumQuantity how many products the sale must include to qualify
     * @param discountPercentage the percentage applied to the sale total once qualified
     * @return the promotion that was just created
     */
    public BulkPurchaseDiscount registerBulkPurchaseDiscount(String id, String name, LocalDate startDate, LocalDate endDate,
                                                              int minimumQuantity, double discountPercentage) {
        BulkPurchaseDiscount promotion = new BulkPurchaseDiscount(minimumQuantity, discountPercentage, id, name, startDate, endDate);
        List<Promotion> promotions = promotionRepository.loadAll();
        promotions.add(promotion);
        promotionRepository.saveAll(promotions);
        return promotion;
    }

    /**
     * Returns every promotion on record, active or not.
     *
     * @return the complete catalog of promotions
     */
    public List<Promotion> listAllPromotions() {
        return promotionRepository.loadAll();
    }

    /**
     * Filters the catalog down to only the promotions that are valid
     * as of today.
     *
     * @return the promotions currently within their validity window
     */
    public List<Promotion> listActivePromotions() {
        List<Promotion> active = new ArrayList<>();
        LocalDate today = LocalDate.now();
        for (Promotion promotion : promotionRepository.loadAll()) {
            if (promotion.isActive(today)) {
                active.add(promotion);
            }
        }
        return active;
    }

    /**
     * Looks at every currently active promotion and works out which one
     * would save the customer the most money on this particular sale.
     *
     * @param sale the sale to evaluate against the active promotions
     * @return the most favorable promotion for this sale, or null if no
     *         active promotion applies (i.e. every one of them would
     *         yield a discount of zero)
     */
    public Promotion findBestPromotionFor(Sale sale) {
        Promotion bestPromotion = null;
        double bestDiscount = 0.0;

        for (Promotion promotion : listActivePromotions()) {
            double discount = promotion.calculateDiscount(sale);
            if (discount > bestDiscount) {
                bestDiscount = discount;
                bestPromotion = promotion;
            }
        }

        return bestPromotion;
    }

    /**
     * Retrieves a single promotion by its identifier.
     *
     * @param id the promotion's unique identifier
     * @return the matching promotion, or null if none is found
     */
    public Promotion findById(String id) {
        for (Promotion promotion : promotionRepository.loadAll()) {
            if (promotion.getId().equals(id)) {
                return promotion;
            }
        }
        return null;
    }

}