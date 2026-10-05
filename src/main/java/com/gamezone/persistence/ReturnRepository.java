package com.gamezone.persistence;

import com.gamezone.model.Accessory;
import com.gamezone.model.Product;
import com.gamezone.model.Return;
import com.gamezone.model.Sale;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// =========================================================================
// INICIO EJEMPLO EXPOSICIÓN: LOW COUPLING (BAJO ACOPLAMIENTO)
// =========================================================================
/*
// [ANTES - VIOLA EL PRINCIPIO]
// La capa de persistencia importa y depende fuertemente de la capa de servicios.
import com.gamezone.service.AccessoryService;
import com.gamezone.service.ProductService;
import com.gamezone.service.SaleService;
*/

// [DESPUÉS - APLICA EL PRINCIPIO]
// Definimos interfaces que describen únicamente lo que el repositorio necesita.
// Esto logra un Bajo Acoplamiento: ReturnRepository ya no sabe nada de la capa 'service'.





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
    
    public interface SaleLookup {
        Sale findById(String id);
    }

    public interface ProductLookup {
        Product findById(String id);
    }
// =========================================================================
// FIN EJEMPLO EXPOSICIÓN
// =========================================================================

    private static String FILE_PATH = "data/returns.csv";
    private static String PRODUCT_SEPARATOR = ";";

    // =========================================================================
    // CAMBIO 2: ATRIBUTOS Y CONSTRUCTOR
    // =========================================================================

    /*
    // [ANTES - VIOLA EL PRINCIPIO]
    // Obligaba a inyectar toda la lógica de negocio al repositorio.
    private SaleService saleService;
    private ProductService productService;
    private AccessoryService accessoryService;

    public ReturnRepository(SaleService saleService, ProductService productService, AccessoryService accessoryService) {
        this.saleService = saleService;
        this.productService = productService;
        this.accessoryService = accessoryService;
    }
    */

    // [DESPUÉS - APLICA EL PRINCIPIO]
    // Ahora solo recibe los contratos abstractos. Bajo Acoplamiento puro.
    private final SaleLookup sales;
    private final ProductLookup products;

    public ReturnRepository(SaleLookup sales, ProductLookup products) {
        this.sales = sales;
        this.products = products;
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

        // =========================================================================
        // CAMBIO 3: USO DENTRO DEL MÉTODO PARSELINE
        // =========================================================================
        /*
        // [ANTES - VIOLA EL PRINCIPIO]
        Sale originalSale = saleService.getSaleById(saleId);
        */
        
        // [DESPUÉS]
        // Usa la interfaz SaleLookup
        Sale originalSale = sales.findById(saleId);

        List<Product> returnedProducts = new ArrayList<>();
        if (!productIdsField.isBlank()) {
            String[] productIds = productIdsField.split(PRODUCT_SEPARATOR);
            for (String productId : productIds) {
                /*
                // [ANTES - VIOLA EL PRINCIPIO]
                // Llamaba a un método feo que estaba acoplado a dos servicios distintos
                Product found = findProductOrAccessoryById(productId);
                */
                
                // [DESPUÉS]
                // Usa la interfaz ProductLookup. ¡Mucho más limpio!
                Product found = products.findById(productId);
                if (found != null) {
                    returnedProducts.add(found);
                }
            }
        }

        return new Return(id, returnDate, originalSale, returnedProducts, reason, refundAmount);
    }

    // =========================================================================
    // CAMBIO 4: ELIMINAR LÓGICA DE NEGOCIO DEL REPOSITORIO
    // =========================================================================
    /* 
    // [ANTES - VIOLA EL PRINCIPIO]
    // Esta lógica de buscar primero en un servicio y luego en otro
    // es lógica de negocio, no de persistencia. Se debe eliminar de aquí.
    
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
    */

}