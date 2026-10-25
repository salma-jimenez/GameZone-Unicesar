# Exception Handling Class Diagram

```mermaid
classDiagram
    class GameZoneException {
        #String errorCode
        +GameZoneException(String message)
        +GameZoneException(String message, String errorCode)
        +String getErrorCode()
    }

    class ResourceNotFoundException {
        +ResourceNotFoundException(String resourceType, String identifier)
    }

    class BusinessRuleException {
        +BusinessRuleException(String message)
    }

    class InvalidDataException {
        +InvalidDataException(String field, String description)
    }

    class PersistenceException {
        +PersistenceException(String message, Throwable cause)
    }

    GameZoneException <|-- ResourceNotFoundException
    GameZoneException <|-- BusinessRuleException
    GameZoneException <|-- InvalidDataException
    GameZoneException <|-- PersistenceException

    class ProductValidator {
        +validateProductData(String id, String title, double price, int stock) void
        +validateStockAvailability(Product product, int requestedQuantity) void
        +validateProductExists(Product product, String productId) void
    }

    class PersonValidator {
        +validatePersonData(String id, String name, String email, String phone) void
        +validatePersonExists(Person person, String personId) void
        +validateUniquePerson(List~Person~ existing, String newId) void
    }

    class SaleValidator {
        +validateSaleData(Customer customer, Seller seller, List~Product~ products) void
        +validateSaleExists(Sale sale, String saleId) void
    }

    class ProductService {
        -ProductRepository repository
        +createProduct(...) void
    }

    class PersonService {
        -PersonRepository repository
        +createPerson(...) void
    }

    class SaleService {
        -SaleRepository repository
        +registerSale(...) void
    }

    class ConsoleMenu {
        +start() void
        -handleException(GameZoneException e) void
    }

    ProductService --> ProductValidator : uses
    PersonService --> PersonValidator : uses
    SaleService --> SaleValidator : uses

    ProductService ..> ResourceNotFoundException : throws
    ProductService ..> InvalidDataException : throws
    PersonService ..> BusinessRuleException : throws
    SaleService ..> PersistenceException : throws

    ConsoleMenu ..> GameZoneException : catches