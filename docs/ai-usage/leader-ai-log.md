# AI Usage Log — Technical Leader
**Team member:** Salma Jiménez Vega
**Role:** Technical Leader (Sales module and system integration)

## Summary of use
I used AI tools as occasional support during implementation, mainly to
resolve command-related doubts, understand compilation/runtime errors I
could not solve on my own, and consult Java documentation. It was not
used to generate the design, answer the guiding questions, or write
complete classes.

## Log of consultations

### 1. Git commands
**Query:** Doubts about the correct Git Flow (creating a feature branch
from develop, pushing each commit immediately, opening a Pull Request).
**Use:** Confirming the exact syntax of commands (`git checkout -b`,
`git push -u origin`, resolving conflicts when merging) before executing
them on the actual repository.
**Own decision:** The branching strategy and team organization were
defined by us; the AI only clarified command syntax.

### 2. Constructor dependency error (module integration)
**Query:** While wiring `WarrantyRepository`, `ReturnRepository`, and
`SaleService` in `Main.java`, I got compilation errors because the
constructors did not match the parameters I was passing (missing or
extra injected dependencies).
**Use:** I asked for help interpreting the compiler error message and
understanding which parameters each constructor expected.
**Own decision:** I decided the instantiation order in `Main.java`
(repositories → services → UI) and which dependencies to inject into
each layer, following the team's class diagram.

### 3. Java documentation
**Query:** Specific doubts about Java API classes used in the project
(for example, `LocalDate`/`LocalDateTime`, `instanceof` to check the
actual type of a product).
**Use:** Clarifying the correct way to use these classes/mechanisms
before applying them in `SaleService` and `Sale`.
**Own decision:** The business logic (when to automatically generate a
basic warranty, how to calculate subtotal/discount) was designed by me
based on the exam requirements.

## Conclusion
AI use was limited to punctual technical support (syntax, errors,
documentation), not design generation or full code generation. I am
able to explain and justify every implementation decision of the sales
and integration module on my own.
