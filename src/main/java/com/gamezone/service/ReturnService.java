package com.gamezone.service;

import com.gamezone.model.Accessory;
import com.gamezone.model.Product;
import com.gamezone.model.Return;
import com.gamezone.model.Sale;
import com.gamezone.persistence.ReturnRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles the business rules around returning products: checking that
 * a return is even allowed, working out the refund, putting the stock
 * back, and reporting on returns already on file.
 *
 * 
 * @author Salomejimenez
 */
public class ReturnService {

    private ReturnRepository returnRepository;
    private SaleService saleService;
    private ProductService productService;
    private AccessoryService accessoryService;
    private int nextReturnNumber = 1;

    /**
     * Wires this service to the repository and the other services
     * it needs to validate and complete a return.
     *
     * @param returnRepository persistence layer for return records
     * @param saleService used to fetch and validate the original sale
     * @param productService used to restore stock on returned products
     * @param accessoryService used to restore stock on returned accessories
     */
    public ReturnService(ReturnRepository returnRepository, SaleService saleService,
                          ProductService productService, AccessoryService accessoryService) {
        this.returnRepository = returnRepository;
        this.saleService = saleService;
        this.productService = productService;
        this.accessoryService = accessoryService;
    }

    /**
     * Processes a new return: makes sure the sale exists and is still
     * within the return window, checks that every requested product
     * actually came from that sale, calculates the refund, restores
     * stock, and saves the record.
     *
     * @param saleId the id of the sale the products were bought in
     * @param productIds the ids of the specific products being returned
     *        (repeat an id if more than one unit of it is returned)
     * @param reason the customer's stated reason for the return
     * @return the newly created and persisted Return
     */
    public Return registerReturn(String saleId, List<String> productIds, String reason) {
        Sale sale = saleService.getSaleById(saleId);

        if (!sale.canBeReturned()) {
            throw new IllegalArgumentException("La venta ya superó el plazo de 30 días para devoluciones.");
        }

        List<Product> returnedProducts = matchProductsToSale(sale, productIds);

        String id = "RET" + String.format("%03d", nextReturnNumber++);
        Return returnRecord = new Return(id, LocalDate.now(), sale, returnedProducts, reason, 0.0);
        returnRecord.calculateRefundAmount();

        restoreStockForReturnedProducts(returnedProducts);

        List<Return> returns = returnRepository.loadAll();
        returns.add(returnRecord);
        returnRepository.saveAll(returns);

        return returnRecord;
    }

    /**
     * Returns every return on record, regardless of sale or customer.
     *
     * @return the complete history of returns
     */
    public List<Return> viewAllReturns() {
        return returnRepository.loadAll();
    }

    /**
     * Filters the return history down to the ones tied to sales made
     * by a specific customer.
     *
     * @param customerId the customer's identifier (matched as text,
     *        since Customer stores its id as an int)
     * @return the returns associated with that customer's sales
     */
    public List<Return> viewReturnsByCustomer(String customerId) {
        List<Return> result = new ArrayList<>();
        for (Return returnRecord : returnRepository.loadAll()) {
            Sale sale = returnRecord.getOriginalSale();
            if (sale != null && sale.getCustomer() != null
                    && String.valueOf(sale.getCustomer().getId()).equals(customerId)) {
                result.add(returnRecord);
            }
        }
        return result;
    }

    /**
     * Filters the return history down to the ones tied to a single
     * specific sale.
     *
     * @param saleId the id of the sale to look up returns for
     * @return the returns associated with that sale
     */
    public List<Return> viewReturnsBySale(String saleId) {
        List<Return> result = new ArrayList<>();
        for (Return returnRecord : returnRepository.loadAll()) {
            if (returnRecord.getOriginalSale() != null
                    && returnRecord.getOriginalSale().getIdSale().equals(saleId)) {
                result.add(returnRecord);
            }
        }
        return result;
    }

        /**
     * Adds up the total final amount of every sale made in the given
     * month and year.
     *
     * @param month the month to report on (1-12)
     * @param year the year to report on
     * @return the total sales amount for that period
     */
    public double calculateMonthlySales(int month, int year) {
        double totalSales = 0.0;
        for (Sale sale : saleService.getAllSales()) {
            if (sale.getDateTime().getMonthValue() == month && sale.getDateTime().getYear() == year) {
                totalSales += sale.getTotalAmount();
            }
        }
        return totalSales;
    }

    /**
     * Adds up the total refunded amount of every return processed in
     * the given month and year.
     *
     * @param month the month to report on (1-12)
     * @param year the year to report on
     * @return the total refunded amount for that period
     */
    public double calculateMonthlyReturns(int month, int year) {
        double totalReturns = 0.0;
        for (Return returnRecord : returnRepository.loadAll()) {
            if (returnRecord.getReturnDate().getMonthValue() == month && returnRecord.getReturnDate().getYear() == year) {
                totalReturns += returnRecord.getRefundAmount();
            }
        }
        return totalReturns;
    }

    /**
     * Calculates the store's net balance for the given month and year:
     * total sales minus total refunds.
     *
     * @param month the month to report on (1-12)
     * @param year the year to report on
     * @return total sales minus total returns for that period
     */
    public double generateMonthlyBalance(int month, int year) {
        return calculateMonthlySales(month, year) - calculateMonthlyReturns(month, year);
    }

    /**
     * Confirms that every requested product id actually belongs to the
     * given sale, consuming one matching unit per id so the same
     * product can't be "found" twice if it was only bought once.
     *
     * @param sale the original sale to check against
     * @param productIds the ids the customer wants to return
     * @return the actual Product instances that were matched
     */
    private List<Product> matchProductsToSale(Sale sale, List<String> productIds) {
        List<Product> saleProducts = new ArrayList<>(sale.getProduct());
        List<Product> matched = new ArrayList<>();

        for (String productId : productIds) {
            Product found = null;
            for (Product candidate : saleProducts) {
                if (candidate.getId().equals(productId)) {
                    found = candidate;
                    break;
                }
            }
            if (found == null) {
                throw new IllegalArgumentException("El producto " + productId + " no pertenece a la venta indicada.");
            }
            saleProducts.remove(found);
            matched.add(found);
        }

        return matched;
    }

    /**
     * Puts the stock back for each returned item, delegating to the
     * service that actually owns that item's inventory: accessories
     * go through AccessoryService, regular products through
     * ProductService, since each keeps its own separate catalog.
     *
     * @param returnedProducts the items being returned
     */
    private void restoreStockForReturnedProducts(List<Product> returnedProducts) {
        for (Product product : returnedProducts) {
            if (product instanceof Accessory) {
                accessoryService.restoreStock(product.getId(), 1);
            } else {
                productService.restoreStock(product.getId(), 1);
            }
        }
    }

}