1. Should the three promotions have distinct calculation rules but share common 
attributes and behaviors? How is this reflected in the class hierarchy design? 
What object-oriented programming mechanism allows each promotion type to calculate 
its discount differently without the rest of the system needing to know the concrete types?
This situation is reflected by creating an abstract base class named Promotion 
that holds common attributes like ID, description, start date, end date, and discount 
rate, while specific promotion subclasses extend it to implement their unique calculation 
rules. The object-oriented programming mechanism that enables this is polymorphism 
through method overriding, allowing the system to invoke the discount calculation 
method on a general Promotion reference without needing to know its concrete subclass type.

2. The base class Promotion cannot implement the discount calculation method 
because each type has a different logic. How is this method declared in the 
base class and what does this declaration guarantee regarding subclasses?
This method is declared as an abstract method in the base class, meaning it 
defines only the signature without a body and requires the base class itself 
to be abstract. This declaration guarantees that every concrete subclass is 
strictly forced by the compiler to implement its own version of the discount 
calculation method, ensuring architectural consistency.

3. The business rule states that only the promotion with the highest discount 
is applied. In which class is this selection logic located and why is this 
location coherent with the layered architecture principle? Why should this 
logic NOT be in the Sale class or the console menu?
This selection logic is located in the PromotionService class because business 
rules and processing algorithms belong to the service layer, maintaining a clean 
separation of concerns away from data models and user interfaces. This logic 
should not be in the Sale class because Sale is merely a data container, nor 
in the console menu because the UI layer must only handle user input and output 
without containing business decisions.

4. What modifications are necessary in the Sale class and the generateReceipt 
method so that the receipt shows the applied discount? Do these modifications 
break any existing behavior in the system?
Modifications in the Sale class involve adding attributes to store the applied 
discount and final calculated total, while the generateReceipt method needs to 
be updated to format and display these new financial details. These modifications 
do not break existing behavior because they are purely additive and extend the 
data presentation without altering the core signatures or logic of previous methods.

5. Current promotions are determined by comparing the current date with the 
start and end dates of each promotion. Where is this validation performed 
(in the Promotion class, in the PromotionService, or both)? Justify.
This validation is primarily coordinated within the PromotionService class, 
which evaluates the active promotions from the repository, while the Promotion 
class can contain a simple helper method to check if a date falls within its 
specific range. This separation is justified because the service handles business 
workflows and filtering rules, whereas the model handles its own internal state validation.