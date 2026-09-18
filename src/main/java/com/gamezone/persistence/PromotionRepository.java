
package com.gamezone.persistence;
import com.gamezone.model.BulkPurchaseDiscount;
import com.gamezone.model.CategoryDiscount;
import com.gamezone.model.PercentageDiscount;
import com.gamezone.model.Promotion;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Manages how promotions are stored and retrieved from disk. A single
 * discriminator column at the start of each row tells this class which
 * concrete subclass of Promotion to rebuild when the file is read back.
 *
 * @author Salomejimenez
 */
public class PromotionRepository {

    private static String FILE_PATH = "data/promotions.csv";
    private static String PERCENTAGE_TYPE = "PERCENTAGE";
    private static String CATEGORY_TYPE = "CATEGORY";
    private static String BULK_TYPE = "BULK";

   
    /**
     * Writes the full list of promotions to disk, overwriting whatever
     * was previously stored in the CSV file.
     *
     * @param promotions the current promotions to persist
     */
    public void saveAll(List<Promotion> promotions) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (Promotion promotion : promotions) {
                writer.println(buildLine(promotion));
            }
        } catch (IOException e) {
            System.out.println("Error saving promotions: " + e.getMessage());
        }
    }

    
    /**
     * Reads every promotion stored in the CSV file and rebuilds each one
     * as its correct concrete type. If the file has not been created yet,
     * this simply means there is nothing to load.
     *
     * @return the promotions found on disk, or an empty list when the
     *         file does not exist
     */
    public List<Promotion> loadAll() {
        List<Promotion> promotions = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return promotions;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }
                try {
                    promotions.add(parseLine(line));
                } catch (Exception ex) {
                    System.out.println("Línea de promoción mal formada, se omite: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading promotions: " + e.getMessage());
        }

        return promotions;
    }

    /**
     * Turns a single promotion into its CSV representation. The row
     * always starts with a type tag, followed by the shared fields, and
     * ends with whatever extra values that specific promotion type needs.
     *
     * @param promotion the promotion to convert into a line of text
     * @return the resulting CSV row
     */
    private String buildLine(Promotion promotion) {
        String common = promotion.getId() + ","
                + promotion.getName() + ","
                + promotion.getStartDate() + ","
                + promotion.getEndDate();

        if (promotion instanceof PercentageDiscount) {
            PercentageDiscount percentageDiscount = (PercentageDiscount) promotion;
            return PERCENTAGE_TYPE + "," + common + "," + percentageDiscount.getDiscountPercentage() + ",";
        } else if (promotion instanceof CategoryDiscount) {
            CategoryDiscount categoryDiscount = (CategoryDiscount) promotion;
            return CATEGORY_TYPE + "," + common + "," + categoryDiscount.getDiscountPercentage() + "," + categoryDiscount.getTargetCategory();
        } else {
            BulkPurchaseDiscount bulkPurchaseDiscount = (BulkPurchaseDiscount) promotion;
            return BULK_TYPE + "," + common + "," + bulkPurchaseDiscount.getMinimumQuantity() + "," + bulkPurchaseDiscount.getDiscountPercentage();
        }
    }

    /**
     * Reconstructs a Promotion object from one line of the CSV file.
     * The first column identifies which concrete subclass to instantiate,
     * and the remaining columns are read accordingly.
     *
     * @param line the raw CSV row to interpret
     * @return the promotion described by that row
     */
    private Promotion parseLine(String line) {
        String[] fields = line.split(",", -1);
        String type = fields[0];
        String id = fields[1];
        String name = fields[2];
        LocalDate startDate = LocalDate.parse(fields[3]);
        LocalDate endDate = LocalDate.parse(fields[4]);

        if (type.equals(PERCENTAGE_TYPE)) {
            double discountPercentage = Double.parseDouble(fields[5]);
            return new PercentageDiscount(discountPercentage, id, name, startDate, endDate);
        } else if (type.equals(CATEGORY_TYPE)) {
            double discountPercentage = Double.parseDouble(fields[5]);
            String targetCategory = fields[6];
            return new CategoryDiscount(discountPercentage, targetCategory, id, name, startDate, endDate);
        } else if (type.equals(BULK_TYPE)) {
            int minimumQuantity = Integer.parseInt(fields[5]);
            double discountPercentage = Double.parseDouble(fields[6]);
            return new BulkPurchaseDiscount(minimumQuantity, discountPercentage, id, name, startDate, endDate);
        } else {
            throw new IllegalArgumentException("Unknown promotion type: " + type);
        }
    }
    
}
