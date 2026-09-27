# 🏛️ GameZone-Unicesar - Architectural Layers Diagram
This document presents the unified class diagram for **GameZone-Unicesar**, structured across its 4 architectural layers: **Model**, **Persistence**, **Service**, and **User Interface (UI)**.

## Unified Class Diagram (Mermaid)

```mermaid
classDiagram
    direction TB

    namespace Model {
        class Product {
            -String id
            -String name
            -double price
            -int stock
            +Product(String id, String name, double price, int stock)
            +getId() String
            +setId(String id)
            +getName() String
            +setName(String name)
            +getPrice() double
            +setPrice(double price)
            +getStock() int
            +setStock(int stock)
        }
        class Accessory {
            -String id
            -String name
            -String type
            -double price
            -int stock
            +Accessory(String id, String name, String type, double price, int stock)
            +getId() String
            +setId(String id)
            +getName() String
            +setName(String name)
            +getType() String
            +setType(String type)
            +getPrice() double
            +setPrice(double price)
            +getStock() int
            +setStock(int stock)
        }
        class Person {
            -String documentId
            -String fullName
            -String email
            -String phone
            +Person(String documentId, String fullName, String email, String phone)
            +getDocumentId() String
            +setDocumentId(String documentId)
            +getFullName() String
            +setFullName(String fullName)
            +getEmail() String
            +setEmail(String email)
            +getPhone() String
            +setPhone(String phone)
        }
        class Client {
            -String membershipType
            +Client(String documentId, String fullName, String email, String phone, String membershipType)
            +getMembershipType() String
            +setMembershipType(String membershipType)
        }
        class Seller {
            -String employeeCode
            +Seller(String documentId, String fullName, String email, String phone, String employeeCode)
            +getEmployeeCode() String
            +setEmployeeCode(String employeeCode)
        }
        class Sale {
            -String saleId
            -Person client
            -Person seller
            -String date
            -double totalAmount
            +Sale(String saleId, Person client, Person seller, String date, double totalAmount)
            +getSaleId() String
            +setSaleId(String saleId)
            +getClient() Person
            +setClient(Person client)
            +getSeller() Person
            +setSeller(Person seller)
            +getDate() String
            +setDate(String date)
            +getTotalAmount() double
            +setTotalAmount(double totalAmount)
        }
        class Return {
            -String returnId
            -Sale sale
            -String reason
            -String date
            +Return(String returnId, Sale sale, String reason, String date)
            +getReturnId() String
            +setReturnId(String returnId)
            +getSale() Sale
            +setSale(Sale sale)
            +getReason() String
            +setReason(String reason)
            +getDate() String
            +setDate(String date)
        }
        class Warranty {
            -String warrantyId
            -Product product
            -int durationMonths
            -String status
            +Warranty(String warrantyId, Product product, int durationMonths, String status)
            +getWarrantyId() String
            +setWarrantyId(String warrantyId)
            +getProduct() Product
            +setProduct(Product product)
            +getDurationMonths() int
            +setDurationMonths(int durationMonths)
            +getStatus() String
            +setStatus(String status)
        }
        class Console {
            -String id
            -String name
            -String brand
            -double price
            -int stock
            +Console(String id, String name, String brand, double price, int stock)
            +getId() String
            +setId(String id)
            +getName() String
            +setName(String name)
            +getBrand() String
            +setBrand(String brand)
            +getPrice() double
            +setPrice(double price)
            +getStock() int
            +setStock(int stock)
        }
        class VideoGame {
            -String id
            -String title
            -String platform
            -double price
            -int stock
            +VideoGame(String id, String title, String platform, double price, int stock)
            +getId() String
            +setId(String id)
            +getTitle() String
            +setTitle(String title)
            +getPlatform() String
            +setPlatform(String platform)
            +getPrice() double
            +setPrice(double price)
            +getStock() int
            +setStock(int stock)
        }
        class Controller {
            -String id
            -String name
            -String compatibility
            -double price
            -int stock
            +Controller(String id, String name, String compatibility, double price, int stock)
            +getId() String
            +setId(String id)
            +getName() String
            +setName(String name)
            +getCompatibility() String
            +setCompatibility(String compatibility)
            +getPrice() double
            +setPrice(double price)
            +getStock() int
            +setStock(int stock)
        }
        class Promotion {
            -String promotionId
            -String description
            -double discountPercentage
            -boolean active
            +Promotion(String promotionId, String description, double discountPercentage, boolean active)
            +getPromotionId() String
            +setPromotionId(String promotionId)
            +getDescription() String
            +setDescription(String description)
            +getDiscountPercentage() double
            +setDiscountPercentage(double discountPercentage)
            +isActive() boolean
            +setActive(boolean active)
        }
        class ItemSale {
            -String itemId
            -Product product
            -int quantity
            -double subtotal
            +ItemSale(String itemId, Product product, int quantity, double subtotal)
            +getItemId() String
            +setItemId(String itemId)
            +getProduct() Product
            +setProduct(Product product)
            +getQuantity() int
            +setQuantity(int quantity)
            +getSubtotal() double
            +setSubtotal(double subtotal)
        }
        class Inventory {
            -String inventoryId
            -List~Product~ products
            -String lastUpdateDate
            +Inventory(String inventoryId, List~Product~ products, String lastUpdateDate)
            +getInventoryId() String
            +setInventoryId(String inventoryId)
            +getProducts() List~Product~
            +setProducts(List~Product~ products)
            +getLastUpdateDate() String
            +setLastUpdateDate(String lastUpdateDate)
        }
        class Category {
            -String categoryId
            -String name
            -String description
            +Category(String categoryId, String name, String description)
            +getCategoryId() String
            +setCategoryId(String categoryId)
            +getName() String
            +setName(String name)
            +getDescription() String
            +setDescription(String description)
        }
        class Supplier {
            -String supplierId
            -String companyName
            -String contactPhone
            -String address
            +Supplier(String supplierId, String companyName, String contactPhone, String address)
            +getSupplierId() String
            +setSupplierId(String supplierId)
            +getCompanyName() String
            +setCompanyName(String companyName)
            +getContactPhone() String
            +setContactPhone(String contactPhone)
            +getAddress() String
            +setAddress(String address)
        }
        class Billing {
            -String invoiceNumber
            -Sale sale
            -String paymentMethod
            -double tax
            +Billing(String invoiceNumber, Sale sale, String paymentMethod, double tax)
            +getInvoiceNumber() String
            +setInvoiceNumber(String invoiceNumber)
            +getSale() Sale
            +setSale(Sale sale)
            +getPaymentMethod() String
            +setPaymentMethod(String paymentMethod)
            +getTax() double
            +setTax(double tax)
        }
        class Report {
            -String reportId
            -String reportType
            -String generationDate
            -String content
            +Report(String reportId, String reportType, String generationDate, String content)
            +getReportId() String
            +setReportId(String reportId)
            +getReportType() String
            +setReportType(String reportType)
            +getGenerationDate() String
            +setGenerationDate(String generationDate)
            +getContent() String
            +setContent(String content)
        }
        class Store {
            -String storeId
            -String storeName
            -String location
            -String managerName
            +Store(String storeId, String storeName, String location, String managerName)
            +getStoreId() String
            +setStoreId(String storeId)
            +getStoreName() String
            +setStoreName(String storeName)
            +getLocation() String
            +setLocation(String location)
            +getManagerName() String
            +setManagerName(String managerName)
        }
    }

    namespace Persistence {
        class ProductRepository {
            -List~Product~ productList
            +save(Product product)
            +findById(String id) Product
            +findAll() List~Product~
            +update(Product product)
            +delete(String id)
        }
        class AccessoryRepository {
            -List~Accessory~ accessoryList
            +save(Accessory accessory)
            +findById(String id) Accessory
            +findAll() List~Accessory~
            +update(Accessory accessory)
            +delete(String id)
        }
        class PersonRepository {
            -List~Person~ personList
            +save(Person person)
            +findByDocumentId(String documentId) Person
            +findAll() List~Person~
            +update(Person person)
            +delete(String documentId)
        }
        class SaleRepository {
            -List~Sale~ saleList
            +save(Sale sale)
            +findById(String saleId) Sale
            +findAll() List~Sale~
            +delete(String saleId)
        }
        class ReturnRepository {
            -List~Return~ returnList
            +save(Return returnObj)
            +findById(String returnId) Return
            +findAll() List~Return~
            +delete(String returnId)
        }
        class WarrantyRepository {
            -List~Warranty~ warrantyList
            +save(Warranty warranty)
            +findById(String warrantyId) Warranty
            +findAll() List~Warranty~
            +update(Warranty warranty)
            +delete(String warrantyId)
        }
        class PromotionRepository {
            -List~Promotion~ promotionList
            +save(Promotion promotion)
            +findById(String promotionId) Promotion
            +findAll() List~Promotion~
            +update(Promotion promotion)
            +delete(String promotionId)
        }
        class InventoryRepository {
            -List~Inventory~ inventoryList
            +save(Inventory inventory)
            +findById(String inventoryId) Inventory
            +findAll() List~Inventory~
            +update(Inventory inventory)
            +delete(String inventoryId)
        }
    }

    namespace Service {
        class ProductService {
            <<Service>>
            -ProductRepository productRepository
            +ProductService(ProductRepository productRepository)
            +registerProduct(Product product)
            +searchProduct(String id) Product
            +listAllProducts() List~Product~
            +updateProduct(Product product)
            +removeProduct(String id)
        }
        class AccessoryService {
            <<Service>>
            -AccessoryRepository accessoryRepository
            +AccessoryService(AccessoryRepository accessoryRepository)
            +registerAccessory(Accessory accessory)
            +searchAccessory(String id) Accessory
            +listAllAccessories() List~Accessory~
            +updateAccessory(Accessory accessory)
            +removeAccessory(String id)
        }
        class PersonService {
            <<Service>>
            -PersonRepository personRepository
            +PersonService(PersonRepository personRepository)
            +registerPerson(Person person)
            +searchPerson(String documentId) Person
            +listAllPersons() List~Person~
            +updatePerson(Person person)
            +removePerson(String documentId)
        }
        class SaleService {
            <<Service>>
            -SaleRepository saleRepository
            -ProductRepository productRepository
            +SaleService(SaleRepository saleRepository, ProductRepository productRepository)
            +processSale(Sale sale) Sale
            +searchSale(String saleId) Sale
            +listAllSales() List~Sale~
            +cancelSale(String saleId)
        }
        class ReturnService {
            <<Service>>
            -ReturnRepository returnRepository
            -SaleRepository saleRepository
            +ReturnService(ReturnRepository returnRepository, SaleRepository saleRepository)
            +processReturn(Return returnObj)
            +searchReturn(String returnId) Return
            +listAllReturns() List~Return~
        }
        class WarrantyService {
            <<Service>>
            -WarrantyRepository warrantyRepository
            +WarrantyService(WarrantyRepository warrantyRepository)
            +registerWarranty(Warranty warranty)
            +searchWarranty(String warrantyId) Warranty
            +listAllWarranties() List~Warranty~
            +updateWarrantyStatus(String warrantyId, String status)
        }
        class PromotionService {
            <<Service>>
            -PromotionRepository promotionRepository
            +PromotionService(PromotionRepository promotionRepository)
            +registerPromotion(Promotion promotion)
            +searchPromotion(String promotionId) Promotion
            +listAllPromotions() List~Promotion~
            +applyDiscount(double originalAmount, Promotion promotion) double
        }
    }

    namespace UI {
        class ConsoleUI {
            <<UI>>
            -Scanner scanner
            -ProductService productService
            -AccessoryService accessoryService
            -PersonService personService
            -SaleService saleService
            -ReturnService returnService
            -WarrantyService warrantyService
            -PromotionService promotionService
            +ConsoleUI(...)
            +showMainMenu()
            +handleProductMenu()
            +handleSaleMenu()
            +handleWarrantyMenu()
            +handleReturnMenu()
            +start()
        }
    }

    Person <|-- Client
    Person <|-- Seller
    Product <|-- Accessory
    Product <|-- Console
    Product <|-- VideoGame
    Product <|-- Controller

    Sale "1" --> "1" Client : client
    Sale "1" --> "1" Seller : seller
    Sale "1" *-- "1..*" ItemSale : items
    ItemSale "*" --> "1" Product : product
    Return "*" --> "1" Sale : sale
    Warranty "*" --> "1" Product : product
    Billing "1" --> "1" Sale : sale
    Inventory "1" o-- "0..*" Product : products

    ProductRepository "1" o-- "0..*" Product : productList
    AccessoryRepository "1" o-- "0..*" Accessory : accessoryList
    PersonRepository "1" o-- "0..*" Person : personList
    SaleRepository "1" o-- "0..*" Sale : saleList
    ReturnRepository "1" o-- "0..*" Return : returnList
    WarrantyRepository "1" o-- "0..*" Warranty : warrantyList
    PromotionRepository "1" o-- "0..*" Promotion : promotionList
    InventoryRepository "1" o-- "0..*" Inventory : inventoryList

    ProductService --> ProductRepository : uses
    AccessoryService --> AccessoryRepository : uses
    PersonService --> PersonRepository : uses
    SaleService --> SaleRepository : uses
    SaleService --> ProductRepository : uses
    ReturnService --> ReturnRepository : uses
    ReturnService --> SaleRepository : uses
    WarrantyService --> WarrantyRepository : uses
    PromotionService --> PromotionRepository : uses

    ConsoleUI --> ProductService : uses
    ConsoleUI --> AccessoryService : uses
    ConsoleUI --> PersonService : uses
    ConsoleUI --> SaleService : uses
    ConsoleUI --> ReturnService : uses
    ConsoleUI --> WarrantyService : uses
    ConsoleUI --> PromotionService : uses