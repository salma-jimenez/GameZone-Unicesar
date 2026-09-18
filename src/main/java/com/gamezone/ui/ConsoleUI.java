package com.gamezone.ui;

import com.gamezone.model.BulkPurchaseDiscount;
import com.gamezone.model.CategoryDiscount;
import com.gamezone.model.PercentageDiscount;
import com.gamezone.model.Product;
import com.gamezone.model.Promotion;
import com.gamezone.model.Return;
import com.gamezone.model.Sale;
import com.gamezone.service.AccessoryService;
import com.gamezone.service.PromotionService;
import com.gamezone.service.ReturnService;
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
    private final ReturnService returnService;

    public ConsoleUI(SaleService saleService, AccessoryService accessoryService, 
                     PromotionService promotionService, ReturnService returnService) {
        this.saleService = saleService;
        this.accessoryService = accessoryService;
        this.promotionService = promotionService;
        this.returnService = returnService;
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
                "5. Gestión de Devoluciones y Balance (Requerimiento 3)\n" +
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
                    case "5" -> showReturnMenu();
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

    // --- SUBMENÚ REQUERIMIENTO 3: GESTIÓN DE DEVOLUCIONES Y BALANCE ---
    private void showReturnMenu() {
        boolean back = false;
        while (!back) {
            String optionStr = JOptionPane.showInputDialog(
                null,
                "--- GESTIÓN DE DEVOLUCIONES Y BALANCE ---\n" +
                "1. Registrar Devolución\n" +
                "2. Consultar Todas las Devoluciones\n" +
                "3. Consultar Devoluciones por Cliente\n" +
                "4. Consultar Devoluciones por Venta\n" +
                "5. Consultar Balance Mensual\n" +
                "0. Volver al Menú Principal\n\n" +
                "Ingrese una opción:",
                "Módulo de Devoluciones",
                JOptionPane.QUESTION_MESSAGE
            );

            if (optionStr == null || optionStr.equals("0")) {
                back = true;
                break;
            }

            try {
                switch (optionStr) {
                    case "1" -> registerReturnUI();
                    case "2" -> showAllReturnsUI();
                    case "3" -> showReturnsByCustomerUI();
                    case "4" -> showReturnsBySaleUI();
                    case "5" -> showMonthlyBalanceUI();
                    default -> JOptionPane.showMessageDialog(null, "Opción no válida.");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error de Devolución", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void registerReturnUI() {
        String saleId = JOptionPane.showInputDialog("Ingrese el ID de la venta original:");
        if (saleId == null || saleId.trim().isEmpty()) return;

        String productsStr = JOptionPane.showInputDialog("Ingrese los IDs de productos a devolver (separados por coma):");
        if (productsStr == null || productsStr.trim().isEmpty()) return;
        List<String> productIds = List.of(productsStr.split("\\s*,\\s*"));

        String reason = JOptionPane.showInputDialog("Ingrese el motivo de la devolución:");
        if (reason == null) return;

        Return returnObj = returnService.registerReturn(saleId, productIds, reason);
        JOptionPane.showMessageDialog(null, returnObj.generateReturnReceipt());
    }

    private void showAllReturnsUI() {
        StringBuilder sb = new StringBuilder("--- Listado de Devoluciones ---\n");
        List<Return> returns = returnService.viewAllReturns();
        if (returns.isEmpty()) {
            sb.append("No hay devoluciones registradas.");
        } else {
            returns.forEach(r -> sb.append(r.generateReturnReceipt()).append("\n\n"));
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    private void showReturnsByCustomerUI() {
        String customerId = JOptionPane.showInputDialog("Ingrese el ID del cliente:");
        if (customerId == null || customerId.trim().isEmpty()) return;

        StringBuilder sb = new StringBuilder("--- Devoluciones del Cliente ---\n");
        List<Return> returns = returnService.viewReturnsByCustomer(customerId);
        if (returns.isEmpty()) {
            sb.append("No se encontraron devoluciones para este cliente.");
        } else {
            returns.forEach(r -> sb.append(r.generateReturnReceipt()).append("\n\n"));
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    private void showReturnsBySaleUI() {
        String saleId = JOptionPane.showInputDialog("Ingrese el ID de la venta:");
        if (saleId == null || saleId.trim().isEmpty()) return;

        StringBuilder sb = new StringBuilder("--- Devoluciones de la Venta ---\n");
        List<Return> returns = returnService.viewReturnsBySale(saleId);
        if (returns.isEmpty()) {
            sb.append("No se encontraron devoluciones para esta venta.");
        } else {
            returns.forEach(r -> sb.append(r.generateReturnReceipt()).append("\n\n"));
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    private void showMonthlyBalanceUI() {
        String monthStr = JOptionPane.showInputDialog("Ingrese el mes (1-12):");
        if (monthStr == null) return;
        int month = Integer.parseInt(monthStr);

        String yearStr = JOptionPane.showInputDialog("Ingrese el año (ej. 2026):");
        if (yearStr == null) return;
        int year = Integer.parseInt(yearStr);

        double balance = returnService.generateMonthlyBalance(month, year);
        JOptionPane.showMessageDialog(null, "El balance neto para el período " + month + "/" + year + " es: $" + String.format("%.2f", balance));
    }
}