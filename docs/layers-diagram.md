---
config:
  theme: forest
---
flowchart TB
 subgraph UI_Layer["UI Layer (com.gamezone.ui)"]
        ConsoleUI["ConsoleUI"]
  end
 subgraph Service_Layer["Service Layer (com.gamezone.service)"]
        ProductService["ProductService"]
        PersonService["PersonService"]
        SaleService["SaleService"]
  end
 subgraph Persistence_Layer["Persistence Layer (com.gamezone.persistence)"]
        ProductRepository["ProductRepository"]
        PersonRepository["PersonRepository"]
        SaleRepository["SaleRepository"]
  end
 subgraph Model_Layer["Model Layer (com.gamezone.model)"]
        Product["Product"]
        Person["Person"]
        Sale["Sale"]
        Client["Client"]
        Seller["Seller"]
        Console["Console"]
        VideoGame["VideoGame"]
  end
    UI_Layer --> Service_Layer
    Service_Layer --> Persistence_Layer & Model_Layer
    Persistence_Layer --> Model_Layer

    style ConsoleUI fill:#d470ff
    style ProductService fill:#6bd5ff
    style PersonService fill:#6bd5ff
    style SaleService fill:#6bd5ff
    style ProductRepository fill:#ff98d1
    style PersonRepository fill:#ff98d1
    style SaleRepository fill:#ff98d1
    style UI_Layer fill:#E1BEE7
    style Service_Layer fill:#BBDEFB
    style Persistence_Layer fill:#FFCDD2
