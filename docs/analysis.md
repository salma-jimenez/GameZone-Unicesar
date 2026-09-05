1. What attributes are common to all people who interact with the
store, and which ones are specific to each type of person? How is
this distinction reflected in a class hierarchy?
The common attributes are:
•    name
•    ID number
•    phone number
Attributes specific to the “customer” type:
•    email address
•    purchase history
“Salesperson” type:
•    employee ID
•    work shift.
The hierarchy would be as follows: the “Person” class would be the parent class, and the “Customer” and “Salesperson”
classes would be the child classes that inherit from the parent class.

2. Should there be a class that represents a “generic person” without
specifying their role? Why or why not? What implications does this
decision have for the ability to instantiate that class?
It doesn’t make sense to create a class without a role, but if we create a class that
represents a generic person—this class would be “Person”—which does have a
role as a parent class, the only implication would be that this class is
abstract and should not be instantiated.

3. What characteristics do all the products sold by the
store have in common, regardless of their type? What characteristics are specific
to each type of product?
Common attributes that must be included for all products (Product Class)
•    Identifier (unique product code)
•    Product title/name
•    Quantity of product available in inventory
•    Product price (required to calculate total sales)
•    Any other attributes shared by all products (for example,
a general description)

Attributes specific to each individual class
-Video Games:
•    Platform for which it was developed
•    Video game genre
•    Recommended age rating

-Console
•    Brand
•    Model
•    Generation

4. Each product type must be able to include a description that incorporates its
specific characteristics. How should this behavior be declared
in the base class to ensure that all subclasses implement it in
their own way? What mechanism in object-oriented programming
allows for this?
By declaring Product as an abstract class with an abstract method such as
getDescription(), the compiler requires any concrete subclass to
implement that method; this is achieved through a mechanism called polymorphism.

5. A sale involves a customer, a salesperson, and one or more products. What
kind of relationships exist between the class representing the sale and the other
classes in the system? Are these relationships inheritance, association,
composition, or some other type? Explain.
A sale involves a customer, a salesperson, and one or more products; this relationship
is an ASSOCIATION. It is a simple association where the Sale class
simply references a Customer object and a Salesperson object; its CARDINALITY
is expressed as a Customer can have many Sales (1-to-many) and a Salesperson
can handle many Sales (1-to-many). With Products, it is an aggregation relationship,
because a sale consists of a set or list of one or more products

These relationships cannot be inheritance, since a sale is not “a type”
of customer or product, nor can they be composition or aggregation, because a
customer and a salesperson can exist independently of the sale

From the perspective of the Sale, the relationship is “many-to-one” with each of them. 
A sale can include several products, and the same product can appear in several 
different sales. This is a many-to-many relationship.

6. Should the sales department be responsible for calculating its own total, or should this
responsibility fall to another department? Explain your decision.
Yes, the sales department should be responsible for calculating its own total since it has all
the necessary data to calculate the total for a sale; this depends directly
on the data it already knows, and it contains a list of its products with their respective
prices and quantities.

7. How is it ensured in the design that a sale cannot be recorded without at
least one product? At what point in the system should this rule be validated?
This restriction is ensured through a validation that checks that the list
of products for that sale is not null or empty at the time it is created.
This validation must be performed in the service layer in the “saleservice” class before
saving the information.

8. How does the design reflect the automatic inventory update when
a sale is recorded? Which classes are involved in this operation?
The design reflects that when a sale is recorded, the
quantity sold is automatically deducted from inventory, and that a product cannot be sold if the available quantity
is insufficient. Therefore, the design must comply with each validation so that
when the sale is recorded, the system can take the quantity sold of
each product and subtract it from the available inventory; the classes involved are
“Product,” “Sale,” and “SaleService.”

9. The system should be organized into four layers: model, persistence, services, and
user interface. What types of classes belong to each layer? What criteria
are used to decide which layer a class should be placed in?
Model
•    Product
•    Person
•    Sale
•    Client
•    Seller
•    Console
•    VideoGame
Persistence
•    ProductRepository
•    PersonRepository
•    SaleRepository
Services
•    ProductService
•    PersonService
•    SaleService
User Interface (UI)
The layered architecture defines the criteria for this separation, since
each component must have a clear responsibility. Therefore, the criteria
used to decide which layer each class belongs to is the task that
class will focus on, with the goal of making the code easier to understand,
maintain, and extend.

10. Why shouldn't the logic for saving and retrieving data from files be included
in the domain classes? What problems arise when these
responsibilities are mixed?
Domain classes define what each element in the system is; they do not concern themselves with how
it is stored or displayed. Furthermore, these classes should have only that single responsibility—
they should not have two responsibilities. If they are mixed, the layered architecture breaks down,
and all the code becomes more complex and difficult to maintain.

11. What dependencies are allowed between layers, and which are
prohibited? Explain the rationale behind the allowed dependencies.
In layered architecture, dependencies always flow in a single direction:
the UI depends on the service, the service depends on persistence, and persistence depends on
the model. What is not allowed is skipping layers; the model does not depend on any other layer.