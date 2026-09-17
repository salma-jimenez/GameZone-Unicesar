package com.gamezone.ui;

import com.gamezone.model.Customer;
import com.gamezone.model.Product;
import com.gamezone.service.SaleService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
                    "--- Módulo de Ventas GameZone ---\n"
                    + "1. Registrar Venta\n"
                    + "2. Ver Todas las Ventas\n"
                    + "3. Salir\n\n"
                    + "Ingrese una opción:",
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

                        String idSale = JOptionPane.showInputDialog("Ingrese el ID de la venta:");
                        String amountStr = JOptionPane.showInputDialog("Ingrese el monto total:");
                        double amount = Double.parseDouble(amountStr);

                        Customer customer = new Customer();
                        customer.setId(JOptionPane.showInputDialog("Ingrese ID del cliente:"));
                        customer.setName(JOptionPane.showInputDialog("Ingrese nombre del cliente:"));
                        customer.setPhone(JOptionPane.showInputDialog("Ingrese teléfono del cliente:"));
                        customer.setEmail(JOptionPane.showInputDialog("Ingrese email del cliente:"));

                        Product product = new Product() {
                            @Override
                            public String getDescription() {
                                return "Producto: " + getTitle() + " | Precio: $" + getPrice();
                            }
                        };
                        product.setId(JOptionPane.showInputDialog("Ingrese ID del producto:"));
                        product.setTitle(JOptionPane.showInputDialog("Ingrese nombre/título del producto:"));

                        String priceStr = JOptionPane.showInputDialog("Ingrese precio del producto:");
                        product.setPrice(Double.parseDouble(priceStr));

                        String stockStr = JOptionPane.showInputDialog("Ingrese cantidad/stock disponible:");
                        product.setQuantityAvailable(Integer.parseInt(stockStr));

                        List<Product> listproduct = new ArrayList<>();
                        listproduct.add(product);

                        saleService.registerSale(idSale, amount, customer, listproduct);

                        JOptionPane.showMessageDialog(null, "Venta registrada con éxito.");
                        break;
                    case "2":
                        StringBuilder builder = new StringBuilder("--- Listado de Ventas ---\n");
                        saleService.getAllSales().forEach(s
                                -> builder.append("ID: ").append(s.getIdSale())
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
