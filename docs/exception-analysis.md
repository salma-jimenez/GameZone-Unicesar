# Exception Handling and Validation Analysis

## 1. Exception Propagation: Checked vs. Unchecked
- **Choice:** `RuntimeException` (unchecked exception) is more appropriate for this project.
- **Justification:** Unchecked exceptions avoid cluttering the method signatures
 (`throws` clause) across all repository, service, and UI layers with repetitive 
boilerplate code. Since validation failures and missing resources represent 
programming/runtime states or user input errors rather than unrecoverable external 
system failures (like network drops), unchecked exceptions keep the architecture 
clean and focused on business logic.
- **Consequence:** Method signatures do not require explicit `throws` declarations, 
allowing exceptions to naturally propagate from the persistence and validation 
layers up to the `ConsoleMenu` interface where they are caught centrally.

## 2. Custom Exception Hierarchy and Design Principles
- **Choice:** A unified custom hierarchy rooted at `GameZoneException` instead 
of generic `IllegalArgumentException`.
- **Principles Applied:** 
  - **Single Responsibility Principle (SRP):** Each exception class represents 
a specific category of error (e.g., data format, business rule violation, 
resource absence, or persistence failure).
  - **Open/Closed Principle (OCP):** New specific error types can be added 
seamlessly by extending `GameZoneException` without modifying existing 
error-handling blocks.
  - **Information Hiding & Readability:** It provides meaningful contextual 
error messages in Spanish directly to the user while keeping technical traces encapsulated.

## 3. Validator Layer Architecture (`com.gamezone.validation`)
- **Invocation Flow:** Validators are invoked directly from the `Service` 
layer right before executing any business operation or state change.
- **Architectural Coherence:** This placement respects the layered 
architecture (`ui -> service -> validation -> model`). Services coordinate 
workflow, while validators encapsulate data and business rule verification, 
keeping services clean.
- **Persistence Access:** Validators **cannot** access the persistence layer. 
Validators only inspect data objects and parameters passed from the service 
layer, keeping concerns strictly isolated.

## 4. Behavior Preservation and Testing Strategy
- **Strategy:** The refactor strictly changes the exception types thrown and 
delegates inline validations to dedicated validator classes without altering 
input requirements, calculation rules, or output formats.
- **Verification Evidence:** All 10 existing console menu operations are 
executed and tested against expected use cases to ensure identical functional 
behavior, complemented by unit/integration tests confirming that invalid inputs 
correctly trigger their respective typed custom exceptions.

## 5. Exception Handling Strategy in `ConsoleMenu`
- **Choice:** Multi-catch blocks or cascading `catch` blocks tailored to 
each specific subclass (`ResourceNotFoundException`, `BusinessRuleException`, 
`InvalidDataException`, `PersistenceException`) rather than a single generic 
`GameZoneException` catch.
- **Justification:** Cascading specific catches allow the user interface 
to display tailored, user-friendly messages in Spanish corresponding 
precisely to the category of failure (e.g., distinguishing an invalid 
input format from a depleted inventory stock or a system storage error).