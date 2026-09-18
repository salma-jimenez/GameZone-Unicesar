```mermaid
classDiagram
direction TB
	namespace model {
        class Person {
	        -String id
	        -String name
	        -String phone
	        +getId() String
	        +getName() String
	        +getPhone() String
        }

        class Customer {
	        -String email
	        +getEmail() String
        }

        class Employee {
	        -String employeeCode
	        -String shift
	        +getEmployeeCode() String
	        +getShift() String
        }

        class Product {
	        -String id
	        -String title
	        -double price
	        -int quantityAvailable
	        +getId() String
	        +getTitle() String
	        +getPrice() double
	        +getQuantityAvailable() int
	        +getDescription() String*
	        +updateStock(int quantityAvailable) void
        }

        class VideoGame {
	        -String platform
	        -String genre
	        -String ageRating
	        +getPlatform() String
	        +getGenre() String
	        +getAgeRating() String
	        +getDescription() String
        }

        class Console {
	        -String brand
	        -String model
	        -String generation
	        +getBrand() String
	        +getModel() String
	        +getGeneration() String
	        +getDescription() String
        }

        class Promotion {
	        -String id
	        -String name
	        -LocalDate startDate
	        -LocalDate endDate
	        +getId() String
	        +getName() String
	        +getStartDate() LocalDate
	        +getEndDate() LocalDate
	        +isActive(LocalDate date) boolean
	        +calculateDiscount(Sale sale) double*
        }

        class PercentageDiscount {
	        -double discountPercentage
	        +getDiscountPercentage() double
	        +calculateDiscount(Sale sale) double
        }

        class CategoryDiscount {
	        -double discountPercentage
	        -String targetCategory
	        +getDiscountPercentage() double
	        +getTargetCategory() String
	        +calculateDiscount(Sale sale) double
        }

        class BulkPurchaseDiscount {
	        -int minimumQuantity
	        -double discountPercentage
	        +getMinimumQuantity() int
	        +getDiscountPercentage() double
	        +calculateDiscount(Sale sale) double
        }

        class Sale {
	        -String id
	        -LocalDateTime dateTime
	        -double totalAmount
	        -List~Product~ products
	        -String appliedPromotionName
	        -double discountAmount
	        +getId() String
	        +getDateTime() LocalDateTime
	        +getTotalAmount() double
	        +getProducts() List~Product~
	        +getAppliedPromotionName() String
	        +getDiscountAmount() double
	        +generateReceipt() String
	        +canBeReturned() boolean
        }
	}

	namespace persistence {
        class ProductPersistence {
	        -String filePath
	        +save(List~Product~ products) void
	        +load() List~Product~
        }

        class PersonPersistence {
	        -String filePath
	        +save(List~Person~ persons) void
	        +load() List~Person~
        }

        class SalePersistence {
	        -String filePath
	        +save(List~Sale~ sales) void
	        +load() List~Sale~
        }

        class PromotionRepository {
	        -String filePath
	        +saveAll(List~Promotion~ promotions) void
	        +loadAll() List~Promotion~
        }
	}

	namespace service {
        class ProductService {
	        -ProductPersistence productPersistence
	        +registerProduct(Product product) void
	        +getAllProducts() List~Product~
	        +updateStock(String productId, int quantity) void
        }

        class PersonService {
	        -PersonPersistence personPersistence
	        +registerCustomer(Customer customer) void
	        +registerEmployee(Employee employee) void
	        +getAllCustomers() List~Customer~
	        +getAllEmployees() List~Employee~
        }

        class PromotionService {
	        -PromotionRepository promotionRepository
	        +registerPercentageDiscount(...) void
	        +registerCategoryDiscount(...) void
	        +registerBulkPurchaseDiscount(...) void
	        +listAllPromotions() List~Promotion~
	        +listActivePromotions() List~Promotion~
	        +findBestPromotionFor(Sale sale) Promotion
	        +findById(String id) Promotion
        }

        class SaleService {
	        -SalePersistence salePersistence
	        -ProductService productService
	        -PersonService personService
	        -PromotionService promotionService
	        +registerSale(Sale sale) Sale
	        +getAllSales() List~Sale~
        }
	}

	namespace ui {
        class ConsoleUI {
	        -PersonService personService
	        -ProductService productService
	        -SaleService saleService
	        -PromotionService promotionService
	        +start() void
	        +showMenu() void
	        +showPromotionMenu() void
        }
	}

    class Main {
	    +main(String[] args) void$
    }

	<<abstract>> Person
	<<abstract>> Product
	<<abstract>> Promotion

    Person <|-- Customer
    Person <|-- Employee
    Product <|-- VideoGame
    Product <|-- Console

    Promotion <|-- PercentageDiscount
    Promotion <|-- CategoryDiscount
    Promotion <|-- BulkPurchaseDiscount

    Sale "0..*" --> "1" Customer : purchasedBy
    Sale "0..*" --> "1" Employee : handledBy
    Sale "1" o-- "1..*" Product : contains

    ProductPersistence ..> Product
    PersonPersistence ..> Person
    SalePersistence ..> Sale
    PromotionRepository ..> Promotion

    PersonService ..> PersonPersistence
    ProductService ..> ProductPersistence
    SaleService ..> SalePersistence
    SaleService ..> ProductService
    SaleService ..> PersonService
    SaleService ..> PromotionService
    PromotionService ..> PromotionRepository

    ProductService ..> Product
    PersonService ..> Person
    PromotionService ..> Promotion
    PromotionService ..> Sale
    SaleService ..> Sale

    ConsoleUI ..> PersonService
    ConsoleUI ..> ProductService
    ConsoleUI ..> SaleService
    ConsoleUI ..> PromotionService
    Main ..> ConsoleUI
```
