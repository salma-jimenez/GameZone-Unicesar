# Class Diagrams - SOLID Refactoring (GameZone-Unicesar)

## Architecture Overview (Post-Refactoring)

This document presents the Mermaid class diagram showing the design improvements introduced during Phase 2 to resolve SOLID violations.

### Key Refactoring Highlights:
1. **Dependency Inversion Principle (DIP - V-03):** Introduced `ISaleRepository` interface so that `SaleService` no longer depends on concrete storage implementations.
2. **Single Responsibility Principle (SRP - V-01):** Reorganized `ConsoleUI` to delegate dialog prompts, input reading, and exception messaging into distinct internal helper methods (`promptInput`, `showErrorMessage`, `showInformationMessage`).

---

## Post-Refactoring Class Diagram (After SOLID Refactoring)

```mermaid
classDiagram
    namespace Persistence {
        class ISaleRepository {
            <<interface>>
            +save(Sale sale) void
            +findAll() List~Sale~
            +findById(String id) Sale
        }
        
        class SaleRepository {
            -List~Sale~ sales
            +save(Sale sale) void
            +findAll() List~Sale~
            +findById(String id) Sale
        }
    }

    namespace Services {
        class SaleService {
            -ISaleRepository saleRepository
            -ProductService productService
            -AccessoryService accessoryService
            -PromotionService promotionService
            -WarrantyService warrantyService
            +SaleService(ISaleRepository saleRepository, ProductService productService, AccessoryService accessoryService, PromotionService promotionService, WarrantyService warrantyService)
            +registerSale(Sale sale, List~String~ extendedWarrantyProductIds) void
            +registerSale(Sale sale) void
            +getAllSales() List~Sale~
            +getSaleById(String id) Sale
        }
    }

    namespace UI {
        class ConsoleUI {
            -SaleService saleService
            -AccessoryService accessoryService
            -PromotionService promotionService
            -ReturnService returnService
            -WarrantyService warrantyService
            -ProductService productService
            +start() void
            -showMainMenu() String
            -processMainMenuOption(String optionStr) void
            -promptInput(String message) String
            -showErrorMessage(String message) void
            -showInformationMessage(String message) void
        }
    }

    ISaleRepository <|.. SaleRepository : implements
    SaleService --> ISaleRepository : depends on abstraction
    ConsoleUI --> SaleService : uses