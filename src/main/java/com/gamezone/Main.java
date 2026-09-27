package com.gamezone;

import com.gamezone.persistence.*;
import com.gamezone.service.*;
import com.gamezone.ui.ConsoleUI;

/**
 * Main entry point for the GameZone Management Application.
 * Instantiates layers and launches the User Interface.
 * 
 * @author Salma Jiménez Vega
 * @version 1.0
 */
public class Main {

    public static void main(String[] args) {
        // 1. Instanciar Capa de Persistencia (Repositories básicos)
        ProductRepository productRepository = new ProductRepository();
        PersonRepository personRepository = new PersonRepository();
        SaleRepository saleRepository = new SaleRepository();
        AccessoryRepository accessoryRepository = new AccessoryRepository();
        PromotionRepository promotionRepository = new PromotionRepository();

        // Repositorios que requieren dependencias en su constructor según el error:
        WarrantyRepository warrantyRepository = new WarrantyRepository(productRepository, saleRepository);

        // 2. Instanciar Capa de Servicios (Services básicos)
        ProductService productService = new ProductService(productRepository);
        PersonService personService = new PersonService(personRepository);
        WarrantyService warrantyService = new WarrantyService(warrantyRepository);
        AccessoryService accessoryService = new AccessoryService(accessoryRepository);
        PromotionService promotionService = new PromotionService(promotionRepository);

        // SaleService requiere los 5 parámetros exactos que indica el error:
        SaleService saleService = new SaleService(
            saleRepository, 
            productService, 
            accessoryService, 
            promotionService, 
            warrantyService
        );

        // ReturnRepository requiere SaleService y ProductService según el error:
        ReturnRepository returnRepository = new ReturnRepository(saleService, productService);
        ReturnService returnService = new ReturnService(returnRepository, saleService, productService);

        // 3. Instanciar la Capa de Interfaz de Usuario (UI)
        ConsoleUI consoleUI = new ConsoleUI(
            saleService,
            accessoryService,
            promotionService,
            returnService,
            warrantyService,
            productService
        );

        // 4. Iniciar la Aplicación
        consoleUI.start();
    }
}