package com.gamezone.persistence;

import java.time.LocalDate;


 /**
 * Plain, dependency-free snapshot of one warranty row as stored on disk:
 * just the identifiers and the start date, with no reference to the
 * actual Sale or Product objects. WarrantyService is responsible for
 * resolving those references from the ids carried here.
 *
 * 
 * @author Salomejimenez
 */
public class WarrantyRecord {

    private String idWarranty;
    private String type;
    private String productId;
    private String saleId;
    private LocalDate startDate;

    /**
     * Creates a raw warranty record straight from a CSV row's fields.
     *
     * @param idWarranty the warranty's unique identifier
     * @param type the type discriminator ("BASIC" or "EXTENDED")
     * @param productId the id of the product the warranty covers
     * @param saleId the id of the sale the warranty was issued under
     * @param startDate the date the warranty started
     */
    public WarrantyRecord(String idWarranty, String type, String productId, String saleId, LocalDate startDate) {
        this.idWarranty = idWarranty;
        this.type = type;
        this.productId = productId;
        this.saleId = saleId;
        this.startDate = startDate;
    }

    public String getIdWarranty() {
        return idWarranty;
    }

    public String getType() {
        return type;
    }

    public String getProductId() {
        return productId;
    }

    public String getSaleId() {
        return saleId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

}