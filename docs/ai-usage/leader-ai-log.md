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

## AI log for requirements 5 and 6

## 4: Resolving Stuck Git Rebase Loop
- **Date**: September 28, 2026
- **Tool**: Gemini
- **Phase and Branch**: Integration Phase / Branch: `develop`
- **Objective**: Resolve a persistent terminal prompt loop (`Deletion of directory '.git/rebase-merge' failed`) while trying to abort a rebase.
- **Query**: "git rebase --abort / Deletion of directory '.git/rebase-merge' failed. Should I try again? (y/n)"
- **Response**: The AI identified that Windows file locking was preventing Git from removing the temporary directory. It provided a PowerShell command (`Remove-Item -Path ".git/rebase-merge" -Recurse -Force`) to forcibly remove the blocked directory and clear the state.
- **Decision**: Accepted and executed the PowerShell command, successfully unlocking Git and returning `develop` to a clean working state (`working tree clean`).
- **Related Commit**: N/A (Cleanup action)

---

## 5: Verifying Local Repository Sync Status
- **Date**: September 27, 2026
- **Tool**: Gemini
- **Phase and Branch**: Integration Phase / Branch: `develop`
- **Objective**: Confirm if local `develop` was fully updated and synchronized with GitHub remote repository.
- **Query**: "ahora como se si no hay cambios que bajar sigue estan diferente a github"
- **Response**: Suggested executing `git fetch origin` followed by `git status` to safely inspect remote differences without altering local files, explaining the meaning of `up to date`, `behind`, and `ahead`.
- **Decision**: Accepted the advice, executed `git fetch origin` and `git status`, confirming the local environment was completely up to date with `origin/develop`.
- **Related Commit**: N/A (Status verification)

---

## 6: Commit Message Standardization
- **Date**: September 27, 2026
- **Tool**: Gemini
- **Phase and Branch**: Documentation / Branch: `develop`
- **Objective**: Obtain an industry-standard English commit message following Conventional Commits for updating a project title.
- **Query**: "dame un commit para modificacion de titulo / en ingles"
- **Response**: Provided English options including `docs: update project title` and `fix(ui): update application title`.
- **Decision**: Selected `docs: update project title` / `fix(ui): update application title` to keep commit history clean and compliant with standard conventions.
- **Related Commit**: `fix(ui): update application title`

---

## Entry 4: GitHub Pull Request Conflict Resolution Strategy
- **Date**: September 2, 2026
- **Tool**: Gemini
- **Phase and Branch**: Integration Phase / Branch: `docs/integration-documentation` -> `develop`
- **Objective**: Understand the GitHub error message "Can't automatically merge" and determine how to bring documentation changes into `develop`.
- **Query**: "que significa  / como hago el merge desde la terminar de la rama"
- **Response**: Explained that parallel modifications caused merge conflicts in GitHub. Outlined standard terminal merge steps (`git checkout develop`, `git pull`, `git merge <branch>`) and conflict resolution practices.
- **Decision**: Analyzed the strategy and opted for selective file checkout (`git checkout <branch> -- <file>`) to cleanly bring missing documentation files into `develop` without merge overhead.
- **Related Commit**: N/A (Strategic decision)

---

## 7: Selective File Transfer Across Branches
- **Date**: September 27, 2026
- **Tool**: Gemini
- **Phase and Branch**: Integration Phase / Branch: `develop`
- **Objective**: Transfer specific Markdown files (e.g., `docs/integrated-class-diagram.md`) and all branch changes from `docs/integration-documentation` into `develop`.
- **Query**: "hay un md que no me aparece que quiero de esa rama / git checkout docs/integration-documentation -- docs/integrated-class-diagram.md asi? / puedo traer todo lo que modifique y hacer un solo add y un solo commit"
- **Response**: Confirmed the syntax and explained how to use `git checkout docs/integration-documentation -- .` to bring all modified files from the feature branch into the current working directory in a single operation.
- **Decision**: Accepted and applied `git checkout docs/integration-documentation -- .` followed by a single `git add .`, commit, and `git push origin develop`.
- **Related Commit**: `docs: add integration documentation markdown file`
