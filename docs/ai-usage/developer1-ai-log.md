# AI Log / Consultation Record - GameZone Unicesar

## Entry 1 - Phase 1: Architecture & Layer Responsibilities
- **Date:** 2026-09-04
- **Tool:** Gemini
- **Developer:** Luis Guerrero
- **Prompt:** "What are the core design principles for separating responsibilities in a 4-layer Java application (Model, Repository, Service, Client)?"
- **AI Output:** Provided architectural breakdown enforcing strict uni-directional dependencies (UI -> Service -> Repository -> Model) and preventing file IO in model classes.
- **Developer Adaptation:** Designed project package structure under `com.mycompany.gamezone` following these boundaries to ensure clean layer separation.

---

## Entry 2 - Phase 2: Object-Oriented Design for Product Hierarchy
- **Date:** 2026-09-05
- **Tool:** Gemini
- **Developer:** Luis Guerrero
- **Prompt:** "Should base product properties be organized via an abstract class or an interface when child classes share state fields like price and quantity?"
- **AI Output:** Recommended an abstract class (`Product`) to encapsulate shared instance variables (`id`, `title`, `price`, `quantityAvailable`) and common getters/setters while enforcing polymorphic behavior.
- **Developer Adaptation:** Created abstract class `Product` with private attributes, public constructor, and an abstract `getDescription()` method.

---

## Entry 3 - Phase 2: Inherited Constructor Pattern in Subclasses
- **Date:** 2026-09-05
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
- **Date:** 2026-09-09
- **Tool:** Gemini
- **Developer:** Luis Guerrero
- **Prompt:** "How should abstract methods in a base class be documented using JavaDoc in English according to project standards?"
- **AI Output:** Provided standard JavaDoc structure emphasizing contract descriptions over implementation details, including proper `@return` tag usage.
- **Developer Adaptation:** Applied the template to `Product.java`'s `getDescription()` method, adapting the description to define expected string formatting for subclasses.

---

## Entry 9 - Phase 4: JavaDoc Parameter Completeness Check
- **Date:** 2026-09-09
- **Tool:** Gemini
- **Developer:** Luis Guerrero
- **Prompt:** "Check the completed JavaDoc documentation for Product.java and ProductService.java to ensure all tags are fully specified."
- **AI Output:** Identified incomplete `@param` tags in setters (missing parameter descriptions) and in constructor dependency injection.
- **Developer Adaptation:** Manually updated all setter methods in `Product.java` and `ProductService` constructor by adding descriptive text next to each `@param` tag.

---

## Entry 10 - Phase 4: Persistence Storage Format Verification
- **Date:** 2026-09-09
- **Tool:** Gemini
- **Developer:** Luis Guerrero
- **Prompt:** "What are the structural differences in Java handling between custom .txt parsing and .csv files?"
- **AI Output:** Confirmed that delimited `.csv` files share identical `BufferedReader` and `split()` parsing logic as standard `.txt` files.
- **Developer Adaptation:** Verified that existing persistence mechanics in `ProductRepository` meet course requirements for text-based data storage.

---

## Entry 11
- **Date:** 2026-09-26
- **Tool:** Gemini
- **Phase and Branch:** Phase 2 | `feature/accessory-category-discount`
- **Objective:** Modify `CategoryDiscount.calculateDiscount` to support `ACCESSORY` items alongside videogames and consoles.
- **Query:** "What are the specific requirements and implementation steps for updating CategoryDiscount.java under Adjustment A1?"  
- **Response:** Recommended adding an `else if` branch checking `("ACCESSORY".equalsIgnoreCase(targetCategory) && product instanceof Accessory)` to accumulate matching item prices into `categoryTotal`.
- **Decision:** Adopted the `instanceof Accessory` approach. I noticed the provided `else if` block was missing the `categoryTotal += product.getPrice();` statement, so I added the summation manually and updated JavaDoc annotations to include `"ACCESSORY"`.
- **Related Commit:** `feat: add ACCESSORY support to CategoryDiscount calculation`

---

## Entry 12
- **Date:** 2026-09-26
- **Tool:** Gemini
- **Phase and Branch:** Phase 2 | `feature/accessory-category-discount`
- **Objective:** Validate `targetCategory` in `PromotionService.registerCategoryDiscount` to allow only valid categories.
- **Query:** "How can I validate that `targetCategory` in `registerCategoryDiscount` only accepts `VIDEOGAME`, `CONSOLE`, or `ACCESSORY` and throws an `IllegalArgumentException`?"
- **Response:** Provided validation logic checking that `targetCategory` is non-null and matches one of the three allowed category values, throwing an `IllegalArgumentException` if invalid.
- **Decision:** Implemented the strict conditional check in `PromotionService`. I customized the exception message to Spanish ("Error, la categoría objetivo debe ser VIDEOGAME, CONSOLE o ACCESSORY") to meet project UI standards and updated JavaDoc parameters.
- **Related Commit:** `feat: add category validation to registerCategoryDiscount`

---

## Entry 13
- **Date:** 2026-09-26
- **Tool:** Gemini
- **Phase and Branch:** Phase 2 | `feature/accessory-category-discount`
- **Objective:** Determine if `ConsoleUI.java` needed adjustments to allow users to register category discounts for accessories.
- **Query:** "Where in `ConsoleUI.java` should I add the menu prompt option for accessory category discounts?"
- **Response:** Analyzed `ConsoleUI.java` and pointed out that `registerCategoryDiscount()` already contained `(VIDEOGAME/CONSOLE/ACCESSORY)` in its `JOptionPane` dialog prompt.
- **Decision:** Confirmed no code modifications were required in the UI layer, as the existing dialog already prompts for accessories and delegates validation directly to the updated service method.
- **Related Commit:** N/A (UI verification)

---

## Entry 14
- **Date:** 2026-09-26
- **Tool:** Gemini
- **Phase and Branch:** Phase 2 | `feature/accessory-category-discount`
- **Objective:** Add an active accessory category promotion in `data/promotions.csv` for integration testing.
- **Query:** "What is the correct format and date range to add an active accessory category discount entry in promotions.csv?"
- **Response:** Provided the semicolon-separated structure (`ID;Name;StartDate;EndDate;Percentage;TargetCategory`) and advised editing the file directly in NetBeans/VS Code instead of Excel to prevent delimiter and date parsing bugs.
- **Decision:** Added `P04;Descuento Accesorios Gamer;2026-09-21;2026-09-26;15.0;ACCESSORY` directly via NetBeans text editor, ensuring dates covered the project evaluation week (`2026-09-21` to `2026-09-26`).
- **Related Commit:** `feat: add active accessory category promotion to promotions.csv`