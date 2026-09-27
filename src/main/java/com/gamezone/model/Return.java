
package com.gamezone.model;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

/**
 * Represents a product return transaction in the GameZone system.
 * Handles returned products, refund calculations, and return receipts.
 * 
 * @author Luis Guerrero
 * @version 1.0
 */
public class Return {
    private String id;
    private LocalDate returnDate;
    private final Sale originalSale;
    private List<Product> returnedProducts;
    private String reason;
    private double refundAmount;

    /**
     * Constructs a new Return instance with full transaction details.
     * 
     * @param id unique identifier of the return
     * @param returnDate date when the return is processed
     * @param originalSale reference to the original sale transaction
     * @param returnedProducts list of specific products being returned
     * @param reason explanation or motive for the return
     */
    public Return(String id, LocalDate returnDate, Sale originalSale, List<Product> returnedProducts, String reason, double refundAmount) {
        this.id = id;
        this.returnDate = returnDate;
        this.originalSale = originalSale;
        this.returnedProducts = returnedProducts;
        this.reason = reason;
        this.refundAmount = refundAmount;
    }
    
    /**
     * Gets the unique identifier of the return.
     * 
     * @return the return ID
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the date when the return was processed.
     * 
     * @return the return date
     */
    public LocalDate getReturnDate() {
        return returnDate;
    }

    /**
     * Gets the original sale transaction associated with this return.
     * 
     * @return the original sale
     */
    public Sale getOriginalSale() {
        return originalSale;
    }

    /**
     * Gets an unmodifiable list of products being returned.
     * 
     * @return list of returned products
     */
    public List<Product> getReturnedProducts() {
        return Collections.unmodifiableList(returnedProducts);
    }

    /**
     * Gets the reason or motive for the return.
     * 
     * @return the return reason
     */
    public String getReason() {
        return reason;
    }

    /**
     * Gets the total refunded amount in pesos.
     * 
     * @return the refund amount
     */
    public double getRefundAmount() {
        return refundAmount;
    }
    
    /**
     * Calculates the total refund amount considering any proportional discounts
     * applied during the original sale transaction.
     * 
     * @return total refund amount in pesos
     */
    public double calculateRefundAmount() {
        double totalRefund = 0.0;
        if (returnedProducts != null && !returnedProducts.isEmpty()) {
            double discountFactor = 1.0;
            if (originalSale != null && originalSale.getProducts() != null && !originalSale.getProducts().isEmpty()) {
                double saleSubtotal = 0.0;
                for (Product p : originalSale.getProducts()) {
                    if (p != null) {
                        saleSubtotal += p.getPrice();
                    }
                }
                double saleTotal = originalSale.getTotalAmount();
                double totalDiscount = saleSubtotal - saleTotal;
                if (saleSubtotal > 0 && totalDiscount > 0) {
                    discountFactor = 1.0 - (totalDiscount / saleSubtotal);
                }
            }
            for (Product product : returnedProducts) {
                if (product != null) {
                    totalRefund += product.getPrice() * discountFactor;
                }
            }
        }
        this.refundAmount = totalRefund;
        return this.refundAmount;
    }
    
    /**
     * Generates a formatted receipt string in Spanish detailing the return transaction.
     * 
     * @return formatted return receipt details
     */
    public String generateReturnReceipt() {
        StringBuilder sb = new StringBuilder();
        sb.append("========================================\n");
        sb.append("        COMPROBANTE DE DEVOLUCIÓN       \n");
        sb.append("========================================\n");
        sb.append("ID Devolución: ").append(id).append("\n");
        sb.append("Fecha: ").append(returnDate).append("\n");
        sb.append("Venta Original: ").append((originalSale != null) ? originalSale.getId() : "N/A").append("\n");
        sb.append("Motivo: ").append(reason).append("\n");
        sb.append("----------------------------------------\n");
        sb.append("Productos Devueltos:\n");
        
        double discountFactor = 1.0;
        if (originalSale != null && originalSale.getProducts() != null && !originalSale.getProducts().isEmpty()) {
            double saleSubtotal = 0.0;
            for (Product p : originalSale.getProducts()) {
                if (p != null) saleSubtotal += p.getPrice();
            }
            double totalDiscount = saleSubtotal - originalSale.getTotalAmount();
            if (saleSubtotal > 0 && totalDiscount > 0) {
                discountFactor = 1.0 - (totalDiscount / saleSubtotal);
            }
        }
        
        if (returnedProducts != null && !returnedProducts.isEmpty()) {
            for (Product product : returnedProducts) {
                if (product != null) {
                    double listPrice = product.getPrice();
                    double netRefund = listPrice * discountFactor;
                    double itemDiscount = listPrice - netRefund;

                    sb.append(" - ").append(product.getTitle()).append("\n")
                      .append("   Precio lista: $").append(String.format("%.2f", listPrice))
                      .append(" | Desc.: $").append(String.format("%.2f", itemDiscount))
                      .append(" | Neto: $").append(String.format("%.2f", netRefund)).append("\n");
                }
            }
        } else {
            sb.append(" (Ninguno)\n");
        }
        
        sb.append("----------------------------------------\n");
        sb.append("Monto Reembolsado: $").append(String.format("%.2f", refundAmount)).append("\n");
        sb.append("========================================");
        
        return sb.toString();
    }
}
