package com.gamezone.ui;

import com.gamezone.model.Product;
import com.gamezone.model.Promotion;
import com.gamezone.model.Return;
import com.gamezone.model.Sale;
import com.gamezone.service.*;
import javax.swing.JOptionPane;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * User Interface component managing user interaction via JOptionPane.
 * Refactored to address SRP (V-01) by separating menu dispatching and input parsing flows.
 * 
 * @author Salma Jiménez Vega
 */
public class ConsoleUI {
    private final SaleService saleService;
    private final AccessoryService accessoryService;
    private final PromotionService promotionService;
    private final ReturnService returnService;
    private final WarrantyService warrantyService;
    private final ProductService productService;

    public ConsoleUI(SaleService saleService, ProductService productService, AccessoryService accessoryService, 
                     PromotionService promotionService, ReturnService returnService, 
                     WarrantyService warrantyService) {
        this.saleService = saleService;
        this.productService = productService;
        this.accessoryService = accessoryService;
        this.promotionService = promotionService;
        this.returnService = returnService;
        this.warrantyService = warrantyService;
       
    }

    public void start() {
        boolean running = true;
        while (running) {
            String optionStr = showMainMenu();
            if (optionStr == null || optionStr.equals("0")) {
                running = false;
                break;
            }
            processMainMenuOption(optionStr);
        }
    }

    private String showMainMenu() {
        return JOptionPane.showInputDialog(
            null,
            "--- Módulo de Ventas y Gestión GameZone ---\n" +
            "1. Registrar Venta (Taller)\n" +
            "2. Ver Todas las Ventas (Taller)\n" +
            "3. Gestión de Accesorios (Requerimiento 1)\n" +
            "4. Gestión de Promociones (Requerimiento 2)\n" +
            "5. Gestión de Devoluciones y Balance (Requerimiento 3)\n" +
            "6. Gestión de Garantías (Requerimiento 4)\n" +
            "0. Salir\n\n" +
            "Ingrese una opción:",
            "Gestión de Ventas GameZone",
            JOptionPane.QUESTION_MESSAGE
        );
    }

    private void processMainMenuOption(String optionStr) {
        try {
            switch (optionStr) {
                case "1" -> registerSaleUI();
                case "2" -> showAllSalesUI();
                case "3" -> showAccessoryMenu();
                case "4" -> showPromotionMenu();
                case "5" -> showReturnMenu();
                case "6" -> showWarrantyMenu();
                default -> showErrorMessage("Opción no válida.");
            }
        } catch (Exception e) {
            showErrorMessage("Error: " + e.getMessage());
        }
    }

    private void registerSaleUI() {
        String id = promptInput("Ingrese ID de la venta:");
        if (id == null || id.trim().isEmpty()) return;

        List<Product> products = new ArrayList<>();
        List<String> extendedWarrantyProductIds = new ArrayList<>();

        boolean addingProducts = true;
        while (addingProducts) {
            String productId = promptInput("Ingrese el ID del producto a vender (o '0' para finalizar):");
            if (productId == null || productId.equals("0") || productId.trim().isEmpty()) {
                addingProducts = false;
                break;
            }

            Product product = productService.getProductById(productId);
            if (product != null) {
                products.add(product);
                if (product instanceof com.gamezone.model.Console) {
                    int confirm = JOptionPane.showConfirmDialog(
                        null,
                        "¿Desea agregar Garantía Extendida de 12 meses extra para la consola: " + product.getTitle() + "?",
                        "Garantía Extendida",
                        JOptionPane.YES_NO_OPTION
                    );
                    if (confirm == JOptionPane.YES_OPTION) {
                        extendedWarrantyProductIds.add(product.getId());
                    }
                }
            } else {
                showErrorMessage("Producto no encontrado.");
            }
        }

        if (products.isEmpty()) {
            showInformationMessage("No se agregaron productos a la venta.");
            return;
        }

        Sale sale = new Sale(id, LocalDateTime.now(), 0.0, null, products);
        saleService.registerSale(sale, extendedWarrantyProductIds);
        showInformationMessage(sale.generateReceipt());
    }

    private void showAllSalesUI() {
        StringBuilder builder = new StringBuilder("--- Listado de Ventas ---\n");
        saleService.getAllSales().forEach(s -> builder.append(s.generateReceipt()).append("\n\n"));
        showInformationMessage(builder.toString());
    }

    private void showAccessoryMenu() {
        boolean back = false;
        while (!back) {
            String optionStr = promptInput(
                "--- GESTIÓN DE ACCESORIOS ---\n" +
                "1. Listar Accesorios\n" +
                "2. Actualizar Stock de Accesorio\n" +
                "3. Eliminar Accesorio\n" +
                "0. Volver al Menú Principal\n\n" +
                "Ingrese una opción:"
            );
            if (optionStr == null || optionStr.equals("0")) {
                back = true;
                break;
            }
            try {
                switch (optionStr) {
                    case "1" -> listAccessoriesUI();
                    case "2" -> updateAccessoryStockUI();
                    case "3" -> deleteAccessoryUI();
                    default -> showErrorMessage("Opción no válida.");
                }
            } catch (Exception e) {
                showErrorMessage("Error en Accesorios: " + e.getMessage());
            }
        }
    }

    private void listAccessoriesUI() {
        StringBuilder sb = new StringBuilder("--- LISTADO DE ACCESORIOS ---\n\n");
        var accessories = accessoryService.getAllAccessories();
        if (accessories.isEmpty()) {
            sb.append("No hay accesorios registrados.");
        } else {
            accessories.forEach(a -> sb.append(a.toString()).append("\n"));
        }
        showInformationMessage(sb.toString());
    }

    private void updateAccessoryStockUI() {
        String id = promptInput("Ingrese el ID del accesorio a actualizar:");
        if (id == null || id.trim().isEmpty()) return;
        String stockStr = promptInput("Ingrese el nuevo stock disponible:");
        if (stockStr == null) return;
        int newStock = Integer.parseInt(stockStr);
        accessoryService.updateStock(id, newStock);
        showInformationMessage("Stock actualizado exitosamente.");
    }

    private void deleteAccessoryUI() {
        String id = promptInput("Ingrese el ID del accesorio a eliminar:");
        if (id == null || id.trim().isEmpty()) return;
        accessoryService.deleteAccessory(id);
        showInformationMessage("Accesorio eliminado exitosamente.");
    }

    private void showPromotionMenu() {
        boolean back = false;
        while (!back) {
            String optionStr = promptInput(
                "--- GESTIÓN DE PROMOCIONES ---\n" +
                "1. Listar Promociones Activas\n" +
                "2. Registrar Nueva Promoción\n" +
                "0. Volver al Menú Principal\n\n" +
                "Ingrese una opción:"
            );
            if (optionStr == null || optionStr.equals("0")) {
                back = true;
                break;
            }
            try {
                switch (optionStr) {
                    case "1" -> listPromotionsUI();
                    case "2" -> registerPromotionUI();
                    default -> showErrorMessage("Opción no válida.");
                }
            } catch (Exception e) {
                showErrorMessage("Error en Promociones: " + e.getMessage());
            }
        }
    }

    private void listPromotionsUI() {
        StringBuilder sb = new StringBuilder("--- PROMOCIONES DISPONIBLES ---\n\n");
        List<Promotion> promotions = promotionService.getAllPromotions();
        if (promotions.isEmpty()) {
            sb.append("No hay promociones registradas.");
        } else {
            promotions.forEach(p -> sb.append(p.toString()).append("\n------------------\n"));
        }
        showInformationMessage(sb.toString());
    }

    private void registerPromotionUI() {
        showInformationMessage("Función de registro de promociones en desarrollo.");
    }

    private void showReturnMenu() {
        boolean back = false;
        while (!back) {
            String optionStr = promptInput(
                "--- GESTIÓN DE DEVOLUCIONES Y BALANCE ---\n" +
                "1. Registrar Devolución\n" +
                "2. Ver Todas las Devoluciones\n" +
                "3. Ver Devoluciones por Cliente\n" +
                "4. Ver Devoluciones por Venta\n" +
                "5. Generar Balance Neto Mensual\n" +
                "0. Volver al Menú Principal\n\n" +
                "Ingrese una opción:"
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
                    default -> showErrorMessage("Opción no válida.");
                }
            } catch (Exception e) {
                showErrorMessage("Error en Devoluciones: " + e.getMessage());
            }
        }
    }

    private void registerReturnUI() {
        String saleId = promptInput("Ingrese el ID de la venta a devolver:");
        if (saleId == null || saleId.trim().isEmpty()) return;
        String productsStr = promptInput("Ingrese los IDs de los productos separados por coma:");
        if (productsStr == null || productsStr.trim().isEmpty()) return;
        List<String> productIds = List.of(productsStr.split("\\s*,\\s*"));
        String reason = promptInput("Ingrese el motivo de la devolución:");
        if (reason == null) return;
        
        Return returnObj = returnService.registerReturn(saleId, productIds, reason);
        showInformationMessage(returnObj.generateReturnReceipt());
    }

    private void showAllReturnsUI() {
        StringBuilder sb = new StringBuilder("--- Listado de Devoluciones ---\n");
        List<Return> returns = returnService.viewAllReturns();
        if (returns.isEmpty()) {
            sb.append("No hay devoluciones registradas.");
        } else {
            returns.forEach(r -> sb.append(r.generateReturnReceipt()).append("\n\n"));
        }
        showInformationMessage(sb.toString());
    }

    private void showReturnsByCustomerUI() {
        String customerId = promptInput("Ingrese el ID del cliente:");
        if (customerId == null || customerId.trim().isEmpty()) return;
        StringBuilder sb = new StringBuilder("--- Devoluciones del Cliente ---\n");
        List<Return> returns = returnService.viewReturnsByCustomer(customerId);
        if (returns.isEmpty()) {
            sb.append("No se encontraron devoluciones para este cliente.");
        } else {
            returns.forEach(r -> sb.append(r.generateReturnReceipt()).append("\n\n"));
        }
        showInformationMessage(sb.toString());
    }

    private void showReturnsBySaleUI() {
        String saleId = promptInput("Ingrese el ID de la venta:");
        if (saleId == null || saleId.trim().isEmpty()) return;
        StringBuilder sb = new StringBuilder("--- Devoluciones de la Venta ---\n");
        List<Return> returns = returnService.viewReturnsBySale(saleId);
        if (returns.isEmpty()) {
            sb.append("No se encontraron devoluciones para esta venta.");
        } else {
            returns.forEach(r -> sb.append(r.generateReturnReceipt()).append("\n\n"));
        }
        showInformationMessage(sb.toString());
    }

    private void showMonthlyBalanceUI() {
        String monthStr = promptInput("Ingrese el mes (1-12):");
        if (monthStr == null) return;
        int month = Integer.parseInt(monthStr);
        String yearStr = promptInput("Ingrese el año (ej. 2026):");
        if (yearStr == null) return;
        int year = Integer.parseInt(yearStr);
        double balance = returnService.generateMonthlyBalance(month, year);
        showInformationMessage("El balance neto para el período " + month + "/" + year + " es: $" + String.format("%.2f", balance));
    }

    private void showWarrantyMenu() {
        boolean back = false;
        while (!back) {
            String optionStr = promptInput(
                "--- GESTIÓN DE GARANTÍAS ---\n" +
                "1. Consultar Garantía por Producto y Venta\n" +
                "2. Listar Todas las Garantías\n" +
                "3. Listar Garantías Vigentes\n" +
                "4. Listar Garantías Próximas a Vencer\n" +
                "0. Volver al Menú Principal\n\n" +
                "Ingrese una opción:"
            );
            if (optionStr == null || optionStr.equals("0")) {
                back = true;
                break;
            }
            try {
                switch (optionStr) {
                    case "1" -> showWarrantyByProductAndSaleUI();
                    case "2" -> listAllWarrantiesUI();
                    case "3" -> listActiveWarrantiesUI();
                    case "4" -> listExpiringWarrantiesUI();
                    default -> showErrorMessage("Opción no válida.");
                }
            } catch (Exception e) {
                showErrorMessage("Error de Garantías: " + e.getMessage());
            }
        }
    }

    private void showWarrantyByProductAndSaleUI() {
        String saleId = promptInput("Ingrese el ID de la Venta:");
        if (saleId == null || saleId.trim().isEmpty()) return;
        String productId = promptInput("Ingrese el ID del Producto:");
        if (productId == null || productId.trim().isEmpty()) return;
        
        var warranty = warrantyService.findWarrantyByProduct(productId, saleId);
        if (warranty != null) {
            showInformationMessage(warranty.generateWarrantyCertificate());
        } else {
            showInformationMessage("No se encontró garantía para el producto y venta indicados.");
        }
    }

    private void listAllWarrantiesUI() {
        var list = warrantyService.listAllWarranties();
        if (list.isEmpty()) {
            showInformationMessage("No hay garantías registradas.");
            return;
        }
        StringBuilder sb = new StringBuilder("--- TODAS LAS GARANTÍAS ---\n\n");
        list.forEach(w -> sb.append(w.generateWarrantyCertificate()).append("\n------------------\n"));
        showInformationMessage(sb.toString());
    }

    private void listActiveWarrantiesUI() {
        var list = warrantyService.listActiveWarranties();
        if (list.isEmpty()) {
            showInformationMessage("No hay garantías vigentes.");
            return;
        }
        StringBuilder sb = new StringBuilder("--- GARANTÍAS VIGENTES ---\n\n");
        list.forEach(w -> sb.append(w.generateWarrantyCertificate()).append("\n------------------\n"));
        showInformationMessage(sb.toString());
    }

    private void listExpiringWarrantiesUI() {
        String daysStr = promptInput("Ingrese los días de anticipación para el vencimiento (ej. 30):");
        if (daysStr == null || daysStr.trim().isEmpty()) return;
        int days = Integer.parseInt(daysStr);
        var list = warrantyService.listWarrantiesExpiringSoon(days);
        if (list.isEmpty()) {
            showInformationMessage("No hay garantías próximas a vencer en los siguientes " + days + " días.");
            return;
        }
        StringBuilder sb = new StringBuilder("--- GARANTÍAS PRÓXIMAS A VENCER ---\n\n");
        list.forEach(w -> sb.append(w.generateWarrantyCertificate()).append("\n------------------\n"));
        showInformationMessage(sb.toString());
    }

    private String promptInput(String message) {
        return JOptionPane.showInputDialog(null, message, "GameZone", JOptionPane.QUESTION_MESSAGE);
    }

    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showInformationMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "Información", JOptionPane.INFORMATION_MESSAGE);
    }
}