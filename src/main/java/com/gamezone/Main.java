package com.gamezone;

import com.gamezone.persistence.*;
import com.gamezone.service.*;
import com.gamezone.ui.ConsoleUI;

public class Main {
    public static void main(String[] args) {
        // Instanciación de Capas de Persistencia
        SaleRepository saleRepository = new SaleRepository();
        ProductRepository productRepository = new ProductRepository();
        AccessoryRepository accessoryRepository = new AccessoryRepository();
        PromotionRepository promotionRepository = new PromotionRepository();
        WarrantyRepository warrantyRepository = new WarrantyRepository();

        // Instanciación de Servicios con sus respectivos repositorios inyectados
        ProductService productService = new ProductService(productRepository);
        AccessoryService accessoryService = new AccessoryService(accessoryRepository);
        PromotionService promotionService = new PromotionService(promotionRepository);
        WarrantyService warrantyService = new WarrantyService(warrantyRepository);

        // Inyección de dependencias en SaleService (Solución a V-01)
        SaleService saleService = new SaleService(
            saleRepository, 
            productService, 
            accessoryService, 
            promotionService, 
            warrantyService
        );

        // Inicio de la Interfaz de Usuario
        ConsoleUI consoleUI = new ConsoleUI(
            saleService, 
            productService, 
            accessoryService, 
            promotionService, 
            warrantyService
        );
        consoleUI.start();
    }
}
