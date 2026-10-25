# Consolidated GRASP Audit Report - GameZone-Unicesar

## Audit Context
- **Audit Commit:** Current `develop` branch baseline.
- **Auditor:** Salma Jiménez Vega (Technical Leader - Consolidated Report).

---

## Class Coverage Table

| Class Name | Package | Module / Requirement | Responsible Auditor |
| :--- | :--- | :--- | :--- |
| `Sale` | `com.gamezone.model` | Sales (Taller 1 / Promotions) | Leader |
| `SaleRepository` | `com.gamezone.persistence` | Sales (Taller 1) | Leader |
| `SaleService` | `com.gamezone.service` | Sales / Integrations | Leader |
| `ConsoleUI` | `com.gamezone.ui` | UI Integration & Menu | Leader |
| `Main` | `com.gamezone` | Application Entry Point | Leader |
| `Product` | `com.gamezone.model` | Products (Taller 1) | Developer 1 |
| `VideoGame` | `com.gamezone.model` | Products (Taller 1) | Developer 1 |
| `Console` | `com.gamezone.model` | Products (Taller 1) | Developer 1 |
| `ProductRepository` | `com.gamezone.persistence` | Products (Taller 1) | Developer 1 |
| `ProductService` | `com.gamezone.service` | Products (Taller 1) | Developer 1 |
| `Accessory` | `com.gamezone.model` | Accessories (Req 1) | Developer 1 |
| `Controller`, `Cable`, `Memory` | `com.gamezone.model` | Accessories (Req 1) | Developer 1 |
| `Promotion`, `PercentageDiscount`, ... | `com.gamezone.model` | Promotions (Req 2) | Developer 1 |
| `Return` | `com.gamezone.model` | Returns (Req 3) | Developer 1 |
| `Warranty`, `BasicWarranty`, `ExtendedWarranty` | `com.gamezone.model` | Warranties (Req 4) | Developer 1 |
| `Person` | `com.gamezone.model` | Persons (Taller 1) | Developer 2 |
| `Customer` | `com.gamezone.model` | Persons (Taller 1) | Developer 2 |
| `Seller` | `com.gamezone.model` | Persons (Taller 1) | Developer 2 |
| `PersonRepository` | `com.gamezone.persistence` | Persons (Taller 1) | Developer 2 |
| `PersonService` | `com.gamezone.service` | Persons (Taller 1) | Developer 2 |
| `AccessoryRepository`, `AccessoryService` | `com.gamezone.persistence/service` | Accessories (Req 1) | Developer 2 |
| `PromotionRepository`, `PromotionService` | `com.gamezone.persistence/service` | Promotions (Req 2) | Developer 2 |
| `ReturnRepository`, `ReturnService` | `com.gamezone.persistence/service` | Returns (Req 3) | Developer 2 |
| `WarrantyRepository`, `WarrantyService` | `com.gamezone.persistence/service` | Warranties (Req 4) | Developer 2 |

---

## Final Team Verdict by Pattern

| Pattern | Verdict | Summary |
| :--- | :--- | :--- |
| **Information Expert** | Correct | Domain calculations and queries are handled by expert domain objects across all modules. |
| **Creator** | Correct | Factory and service classes manage instantiation of aggregate entities appropriately. |
| **Controller** | Correct | `ConsoleUI` coordinates system events and delegates business operations directly to service layers. |
| **Low Coupling** | Violation | Direct concrete instantiation of repositories and services across boundaries (V-01, V-02). |
| **High Cohesion** | Violation | UI menu classes contain residual formatting and data collection logic (V-03). |
| **Polymorphism** | Correct | Price and warranty calculation strategies use abstract polymorphic method overriding. |
| **Pure Fabrication** | Correct | Repositories successfully isolate persistence logic from pure domain models. |
| **Indirection** | Correct | Service layer acts as an effective intermediary between UI and persistence layers. |
| **Protected Variations**| Correct | Encapsulation rules (private fields, getters/setters) protect internal state variations. |

---

## Definite List of Violations

### V-01: Direct Concrete Repository Coupling in SaleService
- **Pattern:** Low Coupling
- **Source Report:** `leader-audit V-01`
- **Assigned Developer:** Leader
- **Evidence:** `SaleService` directly instantiates `AccessoryRepository`, `PromotionRepository`, and `WarrantyRepository` internally.
- **Explanation:** Direct instantiation of concrete persistence implementations tightly couples the service to specific data stores.
- **Consequence:** Increases rigidity and prevents flexible persistence swapping or isolated unit testing.
- **Agreed Solution:** Apply Constructor Injection to pass repository dependencies or interfaces.

### V-02: Hardcoded Concrete Repositories in Feature Services
- **Pattern:** Low Coupling
- **Source Report:** `developer2-audit V-01`
- **Assigned Developer:** Developer 2
- **Evidence:** `AccessoryService`, `PromotionService`, and `WarrantyService` directly instantiate their respective repository classes using `new`.
- **Explanation:** Violates low coupling by relying on concrete implementations rather than injected dependencies.
- **Consequence:** Modifying storage mechanisms forces changes across all service classes.
- **Agreed Solution:** Refactor service constructors to accept repository instances via dependency injection.

### V-03: Business Validation and Presentation Mixed in ConsoleUI
- **Pattern:** High Cohesion
- **Source Report:** `leader-audit V-02` / Boundary Finding
- **Assigned Developer:** Leader
- **Evidence:** `ConsoleUI` performs direct validation loops and data aggregation before calling service methods.
- **Explanation:** Mixing presentation logic with input validation and orchestration reduces class cohesion.
- **Consequence:** Changes to UI layout or terminal interaction require modifying validation logic.
- **Agreed Solution:** Delegate input validation steps to specific validator classes in the `validation` package.

---

## Boundary Findings
- **Boundary Violation (V-01 / V-02 Interaction):** `SaleService` (owned by Leader) directly interacts with repositories owned by Developer 2 (`AccessoryRepository`, `WarrantyRepository`). The correction is split: Developer 2 ensures repositories expose standard interfaces, while Leader updates `SaleService` constructors to accept injected dependencies.

## Out of Scope Findings
- No functional defects altering observable external behavior were identified during this audit.