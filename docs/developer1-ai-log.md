# AI Log / Consultation Record - GameZone Unicesar

## Entry 1 - Phase 1: Architecture & Layer Responsibilities
- **Date:** 2026-09-03
- **Tool:** Gemini
- **Developer:** Luis Guerrero
- **Prompt:** "What are the core design principles for separating responsibilities in a 4-layer Java application (Model, Repository, Service, Client)?"
- **AI Output:** Provided architectural breakdown enforcing strict uni-directional dependencies (UI -> Service -> Repository -> Model) and preventing file IO in model classes.
- **Developer Adaptation:** Designed project package structure under `com.mycompany.gamezone` following these boundaries to ensure clean layer separation.
---
## Entry 2 - Phase 2: Object-Oriented Design for Product Hierarchy
- **Date:** 2026-09-04
- **Tool:** Gemini
- **Developer:** Luis Guerrero
- **Prompt:** "Should base product properties be organized via an abstract class or an interface when child classes share state fields like price and quantity?"
- **AI Output:** Recommended an abstract class (`Product`) to encapsulate shared instance variables (`id`, `title`, `price`, `quantityAvailable`) and common getters/setters while enforcing polymorphic behavior.
- **Developer Adaptation:** Created abstract class `Product` with private attributes, public constructor, and an abstract `getDescription()` method.
---
## Entry 3 - Phase 2: Inherited Constructor Pattern in Subclasses
- **Date:** 2026-09-04
- **Tool:** Gemini
- **Developer:** Luis Guerrero
- **Prompt:** "How to properly invoke base constructor parameters in VideoGame and Console subclasses in Java?"
- **AI Output:** Demonstrated the usage of `super(id, title, price, quantityAvailable)` as the first line in child class constructors before assigning specific attributes.
- **Developer Adaptation:** Implemented `VideoGame` constructor handling platform, genre, age rating, and passing core attributes upward via `super()`.
---
## Entry 4 - Phase 3: Text File Serialization Strategy
- **Date:** 2026-09-05
- **Tool:** Gemini
- **Developer:** Luis Guerrero
- **Prompt:** "What is an effective pattern for serializing multiple subclass objects into a single flat text file using line discriminators?"
- **AI Output:** Suggested prefixing each line with type tags (e.g., `VIDEOGAME;...` or `CONSOLE;...`) and separating fields with a consistent delimiter like a semicolon.
- **Developer Adaptation:** Applied `instanceof` pattern matching in `ProductRepository.save()` to write polymorphic product lines to `products.txt`.
---
## Entry 5 - Phase 3: Safe Parsing and Deserialization in Load Method
- **Date:** 2026-09-08
- **Tool:** Gemini
- **Developer:** Luis Guerrero
- **Prompt:** "How to handle type conversion safely when reconstructing objects from split string arrays in Java?"
- **AI Output:** Recommended checking array lengths and wrapping `Double.parseDouble()` and `Integer.parseInt()` within controlled parsing structures.
- **Developer Adaptation:** Built `ProductRepository.load()` method with line sanitization (`line.trim().isEmpty()`) and index-based data extraction.
---
## Entry 6 - Phase 3: Service Dependency Injection
- **Date:** 2026-09-08
- **Tool:** Gemini
- **Developer:** Luis Guerrero
- **Prompt:** "What is the recommended way to connect ProductService with ProductRepository without hardcoding direct instance creation?"
- **AI Output:** Suggested constructor-based dependency injection, accepting `ProductRepository` as a parameter to maintain loose coupling.
- **Developer Adaptation:** Added `public ProductService(ProductRepository productRepository)` constructor and stored the reference as a `private final` field.
---
## Entry 7 - Phase 3: Stock Update Logic Optimization
- **Date:** 2026-09-08
- **Tool:** Gemini
- **Developer:** Luis Guerrero
- **Prompt:** "Review the implementation logic for updateStock in ProductService to verify clean code adherence."
- **AI Output:** Verified correct usage of repository loading and saving. Suggested adding a `break;` statement inside the matching `if` block to optimize loop execution once target ID is found.
- **Developer Adaptation:** Retained setter-based stock modification (`p.setQuantityAvailable(quantity)`) and added the `break;` statement.
---
## Entry 8 - Phase 4: JavaDoc Standards for Abstract Methods
- **Date:** 2026-09-08
- **Tool:** Gemini
- **Developer:** Luis Guerrero
- **Prompt:** "How should abstract methods in a base class be documented using JavaDoc in English according to project standards?"
- **AI Output:** Provided standard JavaDoc structure emphasizing contract descriptions over implementation details, including proper `@return` tag usage.
- **Developer Adaptation:** Applied the template to `Product.java`'s `getDescription()` method, adapting the description to define expected string formatting for subclasses.
---
## Entry 9 - Phase 4: JavaDoc Parameter Completeness Check
- **Date:** 2026-09-08
- **Tool:** Gemini
- **Developer:** Luis Guerrero
- **Prompt:** "Check the completed JavaDoc documentation for Product.java and ProductService.java to ensure all tags are fully specified."
- **AI Output:** Identified incomplete `@param` tags in setters (missing parameter descriptions) and in constructor dependency injection.
- **Developer Adaptation:** Manually updated all setter methods in `Product.java` and `ProductService` constructor by adding descriptive text next to each `@param` tag.
---
## Entry 10 - Phase 4: Persistence Storage Format Verification
- **Date:** 2026-09-08
- **Tool:** Gemini
- **Developer:** Luis Guerrero
- **Prompt:** "What are the structural differences in Java handling between custom .txt parsing and .csv files?"
- **AI Output:** Confirmed that delimited `.csv` files share identical `BufferedReader` and `split()` parsing logic as standard `.txt` files.
- **Developer Adaptation:** Verified that existing persistence mechanics in `ProductRepository` meet course requirements for text-based data storage.
