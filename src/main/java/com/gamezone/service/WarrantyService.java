
package com.gamezone.service;

import com.gamezone.model.BasicWarranty;
import com.gamezone.model.ExtendedWarranty;
import com.gamezone.model.Product;
import com.gamezone.model.Sale;
import com.gamezone.model.Warranty;
import com.gamezone.persistence.WarrantyRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles business logic for managing warranties in the GameZone system.
 * Coordinates basic and extended warranty assignments, status verification, and
 * expiration queries by communicating with the {@link WarrantyRepository}.
 * 
 * @author Luis Guerrero
 * @version 1.0
 */
public class WarrantyService {
    private final WarrantyRepository warrantyRepository;

    /**
     * Injects the WarrantyRepository parameter.
     * 
     * @param warrantyRepository the repository used for warranty data persistence
     */
    public WarrantyService(WarrantyRepository warrantyRepository) {
        this.warrantyRepository = warrantyRepository;
    }
    
    /**
     * Creates and persists an automatic basic warranty for the specified 
     * product and sale.
     * 
     * Generates a unique warranty identifier by combining the sale and product IDs.
     * 
     * @param product the product under warranty (typically a console)
     * @param sale sale the sale transaction associated with this warranty
     * @param startDate startDate the date when the warranty coverage begins
     * @return the created and persisted {@link BasicWarranty} instance
     */
    public BasicWarranty assignBasicWarranty(Product product, Sale sale, LocalDate startDate){
        String idWarranty = "WAR-BAS" + sale.getId() + "-" + product.getId();
        List<Warranty> warranties = warrantyRepository.loadAll();
        BasicWarranty basicWarranty = new BasicWarranty(idWarranty, product, sale, startDate);
        warranties.add(basicWarranty);
        warrantyRepository.saveAll(warranties);
        return basicWarranty;
    }
    
    /**
     * Creates and persists an optional extended warranty for the specified product and sale.
     * Generates a unique warranty identifier by combining the sale and product IDs.
     * 
     * @param product the product under extended warranty
     * @param sale the sale transaction associated with this warranty
     * @param startDate the date when the warranty coverage begins
     * @return the created and persisted {@link ExtendedWarranty} instance
     */
    public ExtendedWarranty assignExtendedWarranty(Product product, Sale sale, LocalDate startDate){
        String idWarranty = "WAR-EXT" + sale.getId() + "-" + product.getId();
        List<Warranty> warranties = warrantyRepository.loadAll();
        ExtendedWarranty extendedWarranty = new ExtendedWarranty(idWarranty, product, sale, startDate);
        warranties.add(extendedWarranty);
        warrantyRepository.saveAll(warranties);
        return extendedWarranty;
    }
    
    /**
     * 
     * @param productId
     * @param saleId
     * @return 
     */
    public Warranty findWarrantyByProduct(String productId, String saleId){
        List<Warranty> warranties = warrantyRepository.loadAll();
        for (Warranty warranty : warranties){
            if (warranty.getProduct().getId().equalsIgnoreCase(productId) &&
                warranty.getSale().getId().equalsIgnoreCase(saleId)) {
                return warranty; 
            }
        }
        return null;
    }
    
    /**
     * Returns all warranties registered in the system.
     * 
     * @return list of all warranties
     */
    public List<Warranty> listAllWarranties(){
        return warrantyRepository.loadAll();
    }
    
    /**
     * Returns all warranties that are currently active on today's date.
     * 
     * @return list of active warranties
     */
    public List<Warranty> listActiveWarranties(){
        List<Warranty> allWarranties = warrantyRepository.loadAll();
        List<Warranty> activeWarranties = new ArrayList<>();
        LocalDate today = LocalDate.now();

        for (Warranty warranty : allWarranties) {
            if (warranty.isActive(today)) {
                activeWarranties.add(warranty);
            }
        }
        return activeWarranties;
    }
    
    /**
     * Returns a list of warranties whose expiration date falls within the next 
     * specified number of days from today.
     * 
     * @param daysAhead number of days ahead to check for expiration
     * @return list of warranties expiring soon
     */
    public List<Warranty> listWarrantiesExpiringSoon(int daysAhead) {
        List<Warranty> allWarranties = warrantyRepository.loadAll();
        List<Warranty> expiringWarranties = new ArrayList<>();

        LocalDate today = LocalDate.now();
        LocalDate limitDate = today.plusDays(daysAhead);

        for (Warranty warranty : allWarranties) {
            LocalDate endDate = warranty.getEndDate();
            if (!endDate.isBefore(today) && !endDate.isAfter(limitDate)) {
                expiringWarranties.add(warranty);
            }
        }
        return expiringWarranties;
    }
}
