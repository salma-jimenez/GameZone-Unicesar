package com.gamezone.persistence;

import com.gamezone.model.BasicWarranty;
import com.gamezone.model.ExtendedWarranty;
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
 * Manages persistence in .csv files by reading and writing warranty
 * data, using a defined file path. This class only ever deals with
 * plain identifiers and dates — it has no dependency on
 * ProductRepository, SaleRepository, or any other component, precisely
 * so it cannot take part in a construction cycle with the service
 * layer. Resolving the actual Sale and Product references is left to
 * WarrantyService.
 * 
 * @author Luis Guerrero
 * @version 1.0
 */
public class WarrantyRepository {
    private static final String FILE_PATH = "data/warranties.csv";
    private static final String BASIC_TYPE = "BASIC";
    private static final String EXTENDED_TYPE = "EXTENDED";

    /**
     * Persists the given list of warranties, overwriting the CSV file.
     * Each row includes a type discriminator (BASIC/EXTENDED) so the concrete 
     * subclass can be reconstructed on load.
     * 
     * @param warranties the warranties to save
     */
     public void saveAll(List<Warranty> warranties){
         File file = new File(FILE_PATH);
         if (file.getParentFile() != null && !file.getParentFile().exists()) {
             file.getParentFile().mkdirs();
         }
         try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))){
             for (Warranty warranty : warranties){
                 String type = (warranty instanceof BasicWarranty) ? BASIC_TYPE : EXTENDED_TYPE;
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
      * Loads all warranty rows from the CSV file as raw records, with
      * no Sale or Product resolution attempted. Each record only
      * carries the plain identifiers and start date found in that row.
      * 
      * @return the list of raw warranty records found in the file, or
      * an empty list if the file does not exist
      * 
      */
    public List<WarrantyRecord> loadAll(){
        List<WarrantyRecord> records = new ArrayList<>();
         File file = new File(FILE_PATH);
        if (!file.exists()) {
            return records;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))){
             String line;
             while ((line = br.readLine()) != null){
                 if (line.trim().isEmpty()){
                     continue;
                 }
                 try {
                     String[] data = line.split(",");
                     String type = data[0];
                     String idWarranty = data[1];
                     String productId = data[2];
                     String saleId = data[3];
                     LocalDate startDate = LocalDate.parse(data[4]);

                     records.add(new WarrantyRecord(idWarranty, type, productId, saleId, startDate));
                 } catch (Exception ex) {
                     System.out.println("Línea de garantía mal formada, se omite: " + line);
                 }
             }
        }catch (IOException e){
            System.out.println("Error loading warranties: " + e.getMessage());
        }
        return records;
    }
}