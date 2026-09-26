package com.gamezone.persistence;

import com.gamezone.model.Accessory;
import com.gamezone.model.Product;
import com.gamezone.model.Return;
import com.gamezone.model.Sale;
import com.gamezone.service.AccessoryService;
import com.gamezone.service.ProductService;
import com.gamezone.service.SaleService;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles persistence of Return records in a CSV file. Since a Return
 * only stores its data on disk as plain identifiers, this class needs
 * SaleService, ProductService, and AccessoryService to rebuild the
 * actual Sale and Product/Accessory references each time a record is
 * loaded back.
 * 
 * @author Salomejimenez
 */
public class ReturnRepository {

    private static String FILE_PATH = "data/returns.csv";
    private static String PRODUCT_SEPARATOR = ";";

    private SaleService saleService;
    private ProductService productService;
    private AccessoryService accessoryService;

    /**
     * Wires this repository to the services it needs to resolve the
     * Sale, Product, and Accessory objects referenced by each stored
     * return.
     *
     * @param saleService used to look up the original sale by id
     * @param productService used to look up returned products by id
     * @param accessoryService used to look up returned accessories by id
     */
    public ReturnRepository(SaleService saleService, ProductService productService, AccessoryService accessoryService) {
        this.saleService = saleService;
        this.productService = productService;
        this.accessoryService = accessoryService;
    }

    /**
     * Overwrites the CSV file with the given list of returns.
     *
     * @param returns the current returns to persist
     */
    public void saveAll(List<Return> returns) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (Return returnRecord : returns) {
                writer.println(buildLine(returnRecord));
            }
        } catch (IOException e) {
            System.out.println("Error saving returns: " + e.getMessage());
        }
    }

    /**
     * Reads every return stored in the CSV file, resolving each row's
     * sale and product/accessory references through the injected
     * services.
     *
     * @return the returns found on disk, or an empty list if the file
     *         does not exist yet
     */
    public List<Return> loadAll() {
        List<Return> returns = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return returns;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }
                try {
                    returns.add(parseLine(line));
                } catch (Exception ex) {
                    System.out.println("Línea de devolución mal formada, se omite: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading returns: " + e.getMessage());
        }

        return returns;
    }

    /**
     * Converts a single Return into its CSV representation, storing
     * only the sale's id and the returned products' ids (references
     * are resolved back into real objects on load).
     *
     * @param returnRecord the return to convert into a line of text
     * @return the resulting CSV row
     */
    private String buildLine(Return returnRecord) {
        StringBuilder productIds = new StringBuilder();
        List<Product> returnedProducts = returnRecord.getReturnedProducts();
        for (int i = 0; i < returnedProducts.size(); i++) {
            productIds.append(returnedProducts.get(i).getId());
            if (i < returnedProducts.size() - 1) {
                productIds.append(PRODUCT_SEPARATOR);
            }
        }

        return returnRecord.getId() + ","
                + returnRecord.getReturnDate() + ","
                + returnRecord.getOriginalSale().getIdSale() + ","
                + productIds + ","
                + returnRecord.getReason() + ","
                + returnRecord.getRefundAmount();
    }

    /**
     * Rebuilds a Return object from one CSV row, using the injected
     * services to fetch the actual Sale and Product/Accessory
     * instances that the stored ids refer to.
     *
     * @param line the raw CSV row to interpret
     * @return the return described by that row
     */
    private Return parseLine(String line) {
        String[] fields = line.split(",", -1);
        String id = fields[0];
        LocalDate returnDate = LocalDate.parse(fields[1]);
        String saleId = fields[2];
        String productIdsField = fields[3];
        String reason = fields[4];
        double refundAmount = Double.parseDouble(fields[5]);

        Sale originalSale = saleService.getSaleById(saleId);

        List<Product> returnedProducts = new ArrayList<>();
        if (!productIdsField.isBlank()) {
            String[] productIds = productIdsField.split(PRODUCT_SEPARATOR);
            for (String productId : productIds) {
                Product found = findProductOrAccessoryById(productId);
                if (found != null) {
                    returnedProducts.add(found);
                }
            }
        }

        return new Return(id, returnDate, originalSale, returnedProducts, reason, refundAmount);
    }

    /**
     * Looks up an id first among regular products, and only if it's
     * not found there, among accessories — since a returned item can
     * be either kind, but each catalog is separate.
     *
     * @param productId the id to look up
     * @return the matching Product or Accessory, or null if neither
     *         catalog has it
     */
    private Product findProductOrAccessoryById(String productId) {
        for (Product product : productService.getAllProducts()) {
            if (product.getId().equals(productId)) {
                return product;
            }
        }
        for (Accessory accessory : accessoryService.listAllAccessories()) {
            if (accessory.getId().equals(productId)) {
                return accessory;
            }
        }
        return null;
    }

}