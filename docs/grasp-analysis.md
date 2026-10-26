# GRASP Analysis - GameZone-Unicesar

## 1. Criteria for GRASP Pattern Evaluation and Evidence Requirements
The team uses the following criteria to evaluate whether a code fragment represents 
a GRASP pattern violation, a correct application, or a non-applicable pattern:
- **Violation:** A class or method takes on responsibilities that clearly belong 
elsewhere, resulting in high coupling, low cohesion, or improper delegation 
(e.g., executing business logic or UI prints inside domain model classes, or 
managing direct file operations inside services without persistence abstraction).
- **Correct:** Responsibilities are properly assigned based on information expertise, 
creator rules, or controller separation, fully respecting the 4-layer architecture 
(model, persistence, service, and ui).
- **Not Applicable:** The pattern does not have a valid application point within 
the specific scope of the audited class or module (for example, if a class does 
not receive external system events, the Controller pattern does not apply).
- **Minimum Evidence Required:** Every verdict must be backed by exact class 
names, method names, line numbers, or code snippets from the current state of 
develop, accompanied by a concrete explanation of its current or future impact.

## 2. Boundary Findings
Boundary findings occur when a responsibility or dependency improperly crosses 
the boundaries between different modules or developers' responsibilities 
(e.g., UI or service classes directly depending on low-level storage details or 
tightly coupled peer components). 
- *Identified Case:* Cross-module calls where service layers handle specific 
UI formatting or where repository dependencies leak across domain boundaries. 
These are unified in the consolidated audit and assigned specifically to the 
developer responsible for the affected class.
- *Verification:* We verified cross-module interactions by reviewing method 
call graphs between services (`SaleService`, `ProductService`, `AccessoryService`, 
etc.) and ensuring they communicate strictly through well-defined domain objects 
and repositories without bypassing architectural layers.

## 3. Handling Overlapping Patterns
When a code fragment violates multiple patterns simultaneously (e.g., failing both 
*Low Coupling* and *High Cohesion* due to a bloated method or god class), we record 
the primary violation under its most descriptive GRASP pattern in the individual reports. 
- *Resolution:* During consolidation, we ensure no duplicate findings are created 
for the same structural defect. The guiding pattern for correction is chosen based 
on which design principle best resolves the root cause (typically prioritizing 
Information Expert or High Cohesion first, which naturally improves Coupling and 
Protected Variations).

## 4. Preservation of External Behavior (No-Regression Strategy)
To ensure all refactoring steps preserve the external behavior of the system, the 
team follows a strict no-regression strategy:
- **Scope:** All console menu operations (product management, sales registration, 
accessories, promotions, returns, warranties, and exception handling) are executed 
with standardized test inputs on `develop` to establish baseline outputs.
- **Verification Process:** Post-refactor verifications are executed iteratively 
during each Pull Request using `mvn clean compile` and functional testing of the 
console menu, ensuring identical outputs, messages, data file structures 
(`accessories.csv`, `promotions.csv`), and terminal behavior.

## 5. Refactoring Order and Coordination
Refactorings are scheduled in a bottom-up and dependency-safe sequence to prevent 
compilation errors or broken dependencies:
1. **Model and Persistence layers** are refactored first (fixing data representations 
and repository structures).
2. **Service layer** refactorings are executed second (reorganizing business 
logic and expert assignments).
3. **User Interface (`ConsoleUI` and `Main`)** adjustments are handled last.
- **Coordination:** When a refactoring touches code owned by another developer, 
it is formally coordinated through the consolidated audit plan, and changes are 
integrated sequentially via atomic commits on the shared feature branch to avoid 
merge conflicts.