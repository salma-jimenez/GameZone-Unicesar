1. The two types of warranties have common attributes (dates, associated
product) but also different attributes and behaviors (duration,
coverage, cost). How is this reflected in the design of the class hierarchy?
What mechanism in object-oriented programming allows
each type of warranty to have its own duration without duplicating code?

Both warranties are distinct and have their own attributes; however, they inherit 
attributes from a superclass—which is the abstract class—and also inherit behaviors 
through abstract methods. In this way, both warranties can use these attributes 
and behaviors with the data that needs to be implemented; for example, each can 
have a different product warranty duration without affecting one another. In the 
hierarchy diagram, this is represented as a parent or main class (superclass) 
that relates both warranties, along with two subclasses (child classes), which 
are these two warranties.

2. The business rule states that only game consoles are automatically covered by the basic warranty,
not video games. At what level of the system does this
decision occur, and what Java mechanism is used to verify the actual type of a
product? Explain your answer.

This decision would be made at the service layer, and the “type” variable would 
be used to verify the actual type to which a given product belongs; to do this, 
it would be necessary to load the list of products into the method responsible for that task.

3.The duration of each type of warranty is different (6 months or 12 months). 
How is the expiration date calculated for each subclass? Should this calculation 
be performed in the warranty constructor or in a separate method? Explain your reasoning. 

A method should be created to calculate the respective duration, depending on 
whether it is a 6-month or 12-month warranty. It would be better to do this in a 
separate method that uses a filter to determine which type of warranty is being 
issued—the 6- month or 12-month one.

4. The extended warranty adds a cost equal to 10% of the product price to the total
sales amount. At what point in the sales registration workflow is this
additional cost calculated and applied? What changes are needed to the
SaleService.registerSale method?

It is calculated in the extended warranty class, and in `saleService`, you would 
need to call a method that assigns the extended warranty when requested and add 
the returned additional cost to the total sale amount.

5. The query for “guarantees about to expire” requires iterating over all
guarantees and filtering those whose expiration date is within the next 30 days.
In which class is this method located, and what dependencies does it require? Why
is this location consistent with the layered architecture?

This method would be in the service layer, where a method could be implemented to 
create that list: `listWarrantiesExpiringSoon(int daysAhead): List` — returns 
the warranties whose expiration dates fall within the next “daysAhead” days. 
This makes sense because that is the layer where lists are handled.
