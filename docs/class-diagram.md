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

        class Sale {
	        -String date
	        -double totalAmount
	        +getDate() String
	        +getTotalAmount() double
	        +calculateTotal() double
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

        class SaleService {
	        -SalePersistence salePersistence
	        -ProductService productService
	        -PersonService personService
	        +registerSale(Sale sale) Sale
	        +getAllSales() List~Sale~
        }

	}
	namespace ui {
        class ConsoleUI {
	        -PersonService personService
	        -ProductService productService
	        -SaleService saleService
	        +start() void
	        +showMenu() void
        }

	}
    class Main {
	    +main(String[] args) void$
    }

	<<abstract>> Person
	<<abstract>> Product

    Person <|-- Customer
    Person <|-- Employee
    Product <|-- VideoGame
    Product <|-- Console
    Sale "0..*" --> "1" Customer : purchasedBy
    Sale "0..*" --> "1" Employee : handledBy
    Sale "1" o-- "1..*" Product : contains
    ProductPersistence ..> Product
    PersonPersistence ..> Person
    SalePersistence ..> Sale
    PersonService ..> PersonPersistence
    ProductService ..> ProductPersistence
    SaleService ..> SalePersistence
    SaleService ..> ProductService
    SaleService ..> PersonService
    ProductService ..> Product
    PersonService ..> Person
    SaleService ..> Sale
    ConsoleUI ..> PersonService
    ConsoleUI ..> ProductService
    ConsoleUI ..> SaleService
    Main ..> ConsoleUI