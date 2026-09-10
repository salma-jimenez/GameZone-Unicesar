package com.gamezone.ui;

import com.gamezone.service.SaleService;
import javax.swing.JOptionPane;

/**
 * User Interface component managing user interaction via JOptionPane.
 */
public class ConsoleUI {
    private final SaleService saleService;

    public ConsoleUI(SaleService saleService) {
        this.saleService = saleService;
    }

    public void start() {
        boolean running = true;
        while (running) {
            String optionStr = JOptionPane.showInputDialog(
                null,
                "--- Módulo de Ventas GameZone ---\n" +
                "1. Registrar Venta\n" +
                "2. Ver Todas las Ventas\n" +
                "3. Salir\n\n" +
                "Ingrese una opción:",
                "Gestión de Ventas",
                JOptionPane.QUESTION_MESSAGE
            );

            if (optionStr == null || optionStr.equals("3")) {
                running = false;
                break;
            }

            try {
                switch (optionStr) {
                    case "1":
                        String id = JOptionPane.showInputDialog("Ingrese ID de la venta:");
                        String amountStr = JOptionPane.showInputDialog("Ingrese el monto total:");
                        double amount = Double.parseDouble(amountStr);
                        saleService.registerSale(id, amount);
                        JOptionPane.showMessageDialog(null, "Venta registrada con éxito.");
                        break;
                    case "2":
                        StringBuilder builder = new StringBuilder("--- Listado de Ventas ---\n");
                        saleService.getAllSales().forEach(s -> 
                            builder.append("ID: ").append(s.getId())
                                   .append(" | Monto: $").append(s.getTotalAmount())
                                   .append(" | Fecha: ").append(s.getDateTime()).append("\n")
                        );
                        JOptionPane.showMessageDialog(null, builder.toString());
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Opción no válida.");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error de Validación", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
