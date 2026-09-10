# AI Usage Log - Developer 2 (Person Module)

**Date:** September 10, 2026

## Tool used
Claude (Anthropic)

## Consultations

### 1. Git workflow (commits, push, pull, merge)
- What I asked: how to do add, commit and push of my changes, and what 
  to do when push was rejected (non-fast-forward) because my local 
  branch and the remote one had diverged histories.
- Why I needed it: I have little experience with the terminal and 
  didn't know when to commit or how to resolve the history conflict.
- How I applied it: I learned to use git status before each step to 
  verify which files were modified, to make separate commits per file 
  when needed, and to resolve a merge with git pull when my local 
  branch became out of sync with the remote one.

### 2. JavaDoc syntax and structure
- What I asked: how to correctly write /** */ comments, what the 
  @param, @return and @throws tags are, and exactly where they should 
  go in the code.
- How I applied it: I documented all my classes (Person, Customer, 
  Seller, PersonRepository, PersonService) with descriptions and the 
  corresponding tags according to what each method does.

### 3. Design of the describeRole() abstract method
- What I asked: what concrete behavior made sense to declare as 
  abstract in Person, given that each role (customer/seller) needs to 
  display different information.
- Final decision made: the describeRole() method was implemented, 
  which each subclass overrides with role-specific information.

### 4. Validations: constructor (model) vs. service layer
- What I asked: whether data validations should go in the class 
  constructors or in the service layer, and whether this counts as 
  code duplication.
- How I applied it: I added basic validations (non-empty fields, 
  positive id, email format) in the constructors of Person, Customer 
  and Seller; and added a business validation in PersonService 
  (that the id is not duplicated), which can only be verified by 
  querying the repository.

## Reflection
[For me, it was a little difficult to understand certain terms or get 
the hang of using the terminal; I think I still haven’t gotten used to it. 
I was a little afraid of messing something up in the repository or on my branch, 
but through this workshop, I learned a lot—especially about development, using 
the terminal, and getting a better grasp of Git and GitHub—as well as teamwork 
and how to fulfill different roles.
]
