
package com.gamezone.persistence;

import com.gamezone.model.BasicWarranty;
import com.gamezone.model.ExtendedWarranty;
import com.gamezone.model.Product;
import com.gamezone.model.Sale;
import com.gamezone.model.Warranty;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages persistence in .csv files by reading and writing objects, using a defined file path. 
 * Uses a type column to distinguish BasicWarranty from ExtendedWarranty.
 * 
 * @author Luis Guerrero
 * @version 1.0
 */
public class WarrantyRepository {
    private static final String filePath = "warranties.csv";
    private static final String basicType = "BASIC";
    private static final String extendedType = "EXTENDED";
    
    private ProductRepository productRepository;
    private SaleRepository saleRepository;
    
    /**
     * Creates the repository, injecting the dependencies needed to resolve Product and Sale references when loading warranties back.
     * 
     * @param productRepository repository used to look up products by id
     * @param saleRepository repository used to look up sales by id
     */
    public WarrantyRepository(ProductRepository productRepository, SaleRepository saleRepository) {
        this.productRepository = productRepository;
        this.saleRepository = saleRepository;
    }
    
    /**
     * Persists the given list of warranties, overwriting the CSV file.
     * Each row includes a type discriminator (BASIC/EXTENDED) so the concrete 
     * subclass can be reconstructed on load.
     * 
     * @param warranties the warranties to save
     */
     public void saveAll(List<Warranty> warranties){
         try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))){
             for (Warranty warranty : warranties){
                 String type = (warranty instanceof BasicWarranty) ? basicType : extendedType;
                 bw.write(
                    type + ","
                    + warranty.getIdWarranty() + ","
                    + warranty.getProduct().getId() + ","
                    + warranty.getSale().getId() + ","
                    + warranty.getStartDate()
                );
                 bw.newLine();
             }
         }catch (IOException e){
             System.out.println("Error saving warranties: " + e.getMessage());
         }
     }
    
     /**
      * 
      * @return 
      */
    public List<Warranty> loadAll(){
        List<Warranty> warranties = new ArrayList<>();
         File file = new File(filePath);
        if (!file.exists()) {
            return warranties;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))){
             String line;
             while ((line = br.readLine()) != null){
                 if (line.trim().isEmpty()){
                     continue;
                 }
                 String[] data = line.split(",");
                 String type = data[0];
                 String idWarranty = data[1];
                 Product product = productRepository.findById(data[2]);
                 Sale sale = saleRepository.findById(data[3]);
                 LocalDate startDate = LocalDate.parse(data[4]);
                 
                 Warranty warranty;
                 if (type.equals(basicType)){
                     warranty = new BasicWarranty(idWarranty, product, sale, startDate);
                 }else{
                     warranty = new ExtendedWarranty(idWarranty, product, sale, startDate);
                 }
                 warranties.add(warranty);
             }
        }catch (IOException e){
            System.out.println("Error loading warranties: " + e.getMessage());
        }
        return warranties;
    }
}
