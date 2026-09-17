
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
 * @author Salomejimenez
 */
public class PromotionRepository {

    private static String FILE_PATH = "data/promotions.csv";
    private static String PERCENTAGE_TYPE = "PERCENTAGE";
    private static String CATEGORY_TYPE = "CATEGORY";
    private static String BULK_TYPE = "BULK";

   
    public void saveAll(List<Promotion> promotions) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (Promotion promotion : promotions) {
                writer.println(buildLine(promotion));
            }
        } catch (IOException e) {
            System.out.println("Error saving promotions: " + e.getMessage());
        }
    }

    
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
                promotions.add(parseLine(line));
            }
        } catch (IOException e) {
            System.out.println("Error loading promotions: " + e.getMessage());
        }

        return promotions;
    }

   
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
