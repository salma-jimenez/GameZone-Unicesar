package com.gamezone.ui;

import com.gamezone.model.BulkPurchaseDiscount;
import com.gamezone.model.CategoryDiscount;
import com.gamezone.model.PercentageDiscount;
import com.gamezone.model.Product;
import com.gamezone.model.Promotion;
import com.gamezone.model.Sale;
import com.gamezone.service.AccessoryService;
import com.gamezone.service.PromotionService;
import com.gamezone.service.SaleService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * User Interface component managing user interaction via JOptionPane.
 */
public class ConsoleUI {
    private final SaleService saleService;
    private final AccessoryService accessoryService;
    private final PromotionService promotionService;

    public ConsoleUI(SaleService saleService, AccessoryService accessoryService, PromotionService promotionService) {
        this.saleService = saleService;
        this.accessoryService = accessoryService;
        this.promotionService = promotionService;
    }

    public void start() {
        boolean running = true;
        while (running) {
            String optionStr = JOptionPane.showInputDialog(
                null,
                "--- Módulo de Ventas y Gestión GameZone ---\n" +
                "1. Registrar Venta (Taller)\n" +
                "2. Ver Todas las Ventas (Taller)\n" +
                "3. Gestión de Accesorios (Requerimiento 1)\n" +
                "4. Gestión de Promociones (Requerimiento 2)\n" +
                "5. Módulo Requerimiento 3\n" +
                "6. Módulo Requerimiento 4\n" +
                "0. Salir\n\n" +
                "Ingrese una opción:",
                "Gestión de Ventas GameZone",
                JOptionPane.QUESTION_MESSAGE
            );

            if (optionStr == null || optionStr.equals("0")) {
                running = false;
                break;
            }

            try {
                switch (optionStr) {
                    case "1" -> registerSaleUI();
                    case "2" -> showAllSalesUI();
                    case "3" -> showAccessoryMenu();
                    case "4" -> showPromotionMenu();
                    case "5" -> JOptionPane.showMessageDialog(null, "Módulo para Requerimiento 3.");
                    case "6" -> JOptionPane.showMessageDialog(null, "Módulo para Requerimiento 4.");
                    default -> JOptionPane.showMessageDialog(null, "Opción no válida.");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error de Validación", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // --- OPCIÓN 1 Y 2 ORIGINALES DEL TALLER ---
    private void registerSaleUI() {
        String id = JOptionPane.showInputDialog("Ingrese ID de la venta:");
        if (id == null || id.trim().isEmpty()) return;

        String amountStr = JOptionPane.showInputDialog("Ingrese el monto total:");
        if (amountStr == null) return;
        double amount = Double.parseDouble(amountStr);

        List<Product> emptyList = new ArrayList<>();
        Sale sale = new Sale(id, LocalDateTime.now(), amount, null, emptyList);
        saleService.registerSale(sale);
        
        JOptionPane.showMessageDialog(null, sale.generateReceipt());
    }

    private void showAllSalesUI() {
        StringBuilder builder = new StringBuilder("--- Listado de Ventas ---\n");
        saleService.getAllSales().forEach(s -> 
            builder.append(s.generateReceipt()).append("\n\n")
        );
        JOptionPane.showMessageDialog(null, builder.toString());
    }

    // --- SUBMENÚ REQUERIMIENTO 1: ACCESORIOS ---
    private void showAccessoryMenu() {
        boolean back = false;
        while (!back) {
            String optionStr = JOptionPane.showInputDialog(
                null,
                "--- GESTIÓN DE ACCESORIOS ---\n" +
                "1. Listar Accesorios\n" +
                "2. Actualizar Stock de Accesorio\n" +
                "3. Eliminar Accesorio\n" +
                "0. Volver al Menú Principal\n\n" +
                "Ingrese una opción:",
                "Módulo de Accesorios",
                JOptionPane.QUESTION_MESSAGE
            );

            if (optionStr == null || optionStr.equals("0")) {
                back = true;
                break;
            }

            try {
                switch (optionStr) {
                    case "1" -> listAccessories();
                    case "2" -> updateAccessoryStock();
                    case "3" -> deleteAccessory();
                    default -> JOptionPane.showMessageDialog(null, "Opción no válida.");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void listAccessories() {
        StringBuilder builder = new StringBuilder("--- Listado de Accesorios ---\n");
        accessoryService.getAllAccessories().forEach(acc -> 
            builder.append("ID: ").append(acc.getId())
                   .append(" | ").append(acc.getTitle())
                   .append(" | Precio: $").append(acc.getPrice())
                   .append(" | Stock: ").append(acc.getQuantityAvailable())
                   .append("\n")
        );
        JOptionPane.showMessageDialog(null, builder.toString());
    }

    private void updateAccessoryStock() {
        String id = JOptionPane.showInputDialog("Ingrese el ID del accesorio:");
        if (id == null || id.trim().isEmpty()) return;
        String stockStr = JOptionPane.showInputDialog("Ingrese el nuevo stock:");
        if (stockStr == null) return;
        int newStock = Integer.parseInt(stockStr);
        accessoryService.updateStock(id, newStock);
        JOptionPane.showMessageDialog(null, "Stock actualizado exitosamente.");
    }

    private void deleteAccessory() {
        String id = JOptionPane.showInputDialog("Ingrese el ID del accesorio a eliminar:");
        if (id == null || id.trim().isEmpty()) return;
        accessoryService.deleteAccessory(id);
        JOptionPane.showMessageDialog(null, "Accesorio eliminado exitosamente.");
    }

    // --- SUBMENÚ REQUERIMIENTO 2: GESTIÓN DE PROMOCIONES ---
    private void showPromotionMenu() {
        boolean back = false;
        while (!back) {
            String optionStr = JOptionPane.showInputDialog(
                null,
                "--- GESTIÓN DE PROMOCIONES ---\n" +
                "1. Registrar Promoción por Porcentaje\n" +
                "2. Registrar Promoción por Categoría\n" +
                "3. Registrar Promoción por Volumen\n" +
                "4. Listar Todas las Promociones\n" +
                "5. Listar Promociones Vigentes\n" +
                "0. Volver al Menú Principal\n\n" +
                "Ingrese una opción:",
                "Módulo de Promociones",
                JOptionPane.QUESTION_MESSAGE
            );

            if (optionStr == null || optionStr.equals("0")) {
                back = true;
                break;
            }

            try {
                switch (optionStr) {
                    case "1" -> registerPercentageDiscount();
                    case "2" -> registerCategoryDiscount();
                    case "3" -> registerBulkPurchaseDiscount();
                    case "4" -> listAllPromotions();
                    case "5" -> listActivePromotions();
                    default -> JOptionPane.showMessageDialog(null, "Opción no válida.");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error de Promociones", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void registerPercentageDiscount() {
        String id = JOptionPane.showInputDialog("Ingrese ID de la promoción:");
        String name = JOptionPane.showInputDialog("Ingrese nombre de la promoción:");
        LocalDate start = LocalDate.parse(JOptionPane.showInputDialog("Fecha de inicio (YYYY-MM-DD):"));
        LocalDate end = LocalDate.parse(JOptionPane.showInputDialog("Fecha de fin (YYYY-MM-DD):"));
        double pct = Double.parseDouble(JOptionPane.showInputDialog("Porcentaje de descuento (%):"));

        promotionService.registerPercentageDiscount(id, name, start, end, pct);
        JOptionPane.showMessageDialog(null, "Promoción por porcentaje registrada correctamente.");
    }

    private void registerCategoryDiscount() {
        String id = JOptionPane.showInputDialog("Ingrese ID de la promoción:");
        String name = JOptionPane.showInputDialog("Ingrese nombre de la promoción:");
        LocalDate start = LocalDate.parse(JOptionPane.showInputDialog("Fecha de inicio (YYYY-MM-DD):"));
        LocalDate end = LocalDate.parse(JOptionPane.showInputDialog("Fecha de fin (YYYY-MM-DD):"));
        double pct = Double.parseDouble(JOptionPane.showInputDialog("Porcentaje de descuento (%):"));
        String category = JOptionPane.showInputDialog("Categoría objetivo (VIDEOGAME/CONSOLE/ACCESSORY):");

        promotionService.registerCategoryDiscount(id, name, start, end, pct, category);
        JOptionPane.showMessageDialog(null, "Promoción por categoría registrada correctamente.");
    }

    private void registerBulkPurchaseDiscount() {
        String id = JOptionPane.showInputDialog("Ingrese ID de la promoción:");
        String name = JOptionPane.showInputDialog("Ingrese nombre de la promoción:");
        LocalDate start = LocalDate.parse(JOptionPane.showInputDialog("Fecha de inicio (YYYY-MM-DD):"));
        LocalDate end = LocalDate.parse(JOptionPane.showInputDialog("Fecha de fin (YYYY-MM-DD):"));
        int minQty = Integer.parseInt(JOptionPane.showInputDialog("Cantidad mínima de productos:"));
        double pct = Double.parseDouble(JOptionPane.showInputDialog("Porcentaje de descuento (%):"));

        promotionService.registerBulkPurchaseDiscount(id, name, start, end, minQty, pct);
        JOptionPane.showMessageDialog(null, "Promoción por volumen registrada correctamente.");
    }

    private void listAllPromotions() {
        StringBuilder sb = new StringBuilder("--- Todas las Promociones ---\n");
        for (Promotion p : promotionService.listAllPromotions()) {
            sb.append("ID: ").append(p.getId())
              .append(" | ").append(p.getName())
              .append(" | Del ").append(p.getStartDate())
              .append(" al ").append(p.getEndDate()).append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    private void listActivePromotions() {
        StringBuilder sb = new StringBuilder("--- Promociones Vigentes Hoy ---\n");
        for (Promotion p : promotionService.listActivePromotions()) {
            sb.append("ID: ").append(p.getId())
              .append(" | ").append(p.getName())
              .append(" | Vigente hasta: ").append(p.getEndDate()).append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }
}