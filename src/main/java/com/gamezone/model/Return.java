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
    private double warrantyRefundAmount;

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
        this.warrantyRefundAmount = 0.0;
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
     * Gets the portion of the refund that came specifically from
     * canceling warranties on returned consoles, as opposed to the
     * portion that came from the products themselves.
     * 
     * @return the warranty-related refund amount in pesos
     */
    public double getWarrantyRefundAmount() {
        return warrantyRefundAmount;
    }
    
    /**
     * Sums the prices of all returned products, assigns the result to refundAmount,
     * and returns the calculated value.
     * 
     * @return total refund amount in pesos
     */
    public double calculateRefundAmount() {
        double total = 0.0;
        if (returnedProducts != null) {
            for (Product product : returnedProducts) {
                if (product != null) {
                    total += product.getPrice();
                }
            }
        }
        this.refundAmount = total;
        return this.refundAmount;
    }

    /**
     * Adds a warranty cancellation refund (from returned consoles) on
     * top of whatever product refund was already calculated. Unlike
     * calculateRefundAmount, this does not recompute anything from
     * scratch — it's meant to be called once, after the product refund
     * is already settled.
     * 
     * @param amount the refundable amount from canceled warranties
     */
    public void addWarrantyRefund(double amount) {
        this.warrantyRefundAmount = amount;
        this.refundAmount += amount;
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
        
        if (returnedProducts != null && !returnedProducts.isEmpty()) {
            for (Product product : returnedProducts) {
                sb.append(" - ").append(product.getTitle())
                  .append(" ($").append(String.format("%.2f", product.getPrice())).append(")\n");
            }
        } else {
            sb.append(" (Ninguno)\n");
        }

        if (warrantyRefundAmount > 0) {
            sb.append("----------------------------------------\n");
            sb.append("Reembolso por garantía cancelada: $")
              .append(String.format("%.2f", warrantyRefundAmount)).append("\n");
        }
        
        sb.append("----------------------------------------\n");
        sb.append("Monto Reembolsado: $").append(String.format("%.2f", refundAmount)).append("\n");
        sb.append("========================================");
        
        return sb.toString();
    }
}