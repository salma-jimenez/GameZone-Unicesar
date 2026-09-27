# Integration Analysis - GameZone Unicesar System

This document details the analysis, causes, and applied solutions for the integration
adjustments (A1–A7) required to unify the four independent modules (Accessories, Promotions,
Warranties, and Returns) into the main GameZone system.

A1 - Accessory Category Discount
The Category Discount feature from Requirement 2 originally limited target categories
 strictly to "VIDEOGAME" and "CONSOLE". With the integration of the accessories module,
the store required the capability to launch promotions targeting accessories. This
limitation occurred because the CategoryDiscount class and PromotionService did not 
recognize Accessory instances or the "ACCESSORY" category string. To resolve this, 
CategoryDiscount was updated to admit "ACCESSORY" as a target category and correctly 
identify Accessory instances in calculateDiscount, while PromotionService.registerCategoryDiscount 
was modified to validate that the selected category belongs to one of the three allowed types. 
Additionally, ConsoleMenu was updated to include the accessory option when registering 
a category promotion, and a valid accessory category promotion was added to data/promotions.csv f
or the working week.

A2 - Circular Dependency in the Warranty Module
Implementing Requirement 4 introduced a circular dependency cycle (SaleService to 
WarrantyService to WarrantyRepository to SaleService) because the warranty repository 
needed to resolve references back to sales during data loading. This tight coupling
between repositories and services prevented clean object construction via constructor 
injection in Main. The issue was solved by refactoring WarrantyRepository to persist 
and load strictly the IDs of the sale and the product, completely removing its dependency 
on SaleService. Furthermore, WarrantyService was updated to dynamically resolve Sale 
and Product references using their identifiers by receiving WarrantyRepository, 
SaleRepository, and ProductService through its constructor, allowing proper object 
construction order in Main and updating docs/warranty-class-diagram.md to reflect 
the new decoupled dependencies.

A3 - Unified Sale Registration Flow
Requirements 1, 2, and 4 independently modified SaleService.registerSale, and in 
an integrated environment, the execution order of operations dictates the final 
financial outcome, such as whether discounts apply before or after warranty costs. 
This arose from a lack of a standardized, unified pipeline for processing a sale 
with multiple product types, promotions, and warranties. To fix this, SaleService.registerSale 
was reorganized to execute a strict 8-step sequence that validates items and stock, 
creates the sale and computes the subtotal, consults PromotionService.findBestPromotionFor(sale) 
to register discounts exclusively on items, generates basic and extended warranties and 
sums their costs, computes the final total, updates inventory across product or accessory 
services, and persists both the sale and warranties. Additionally, Sale.generateReceipt 
was updated to display the subtotal, discount name and amount, extended warranty costs, 
and final total, and the sales submenu in ConsoleMenu was enhanced to allow selecting 
products and accessories while prompting for console warranties.

A4 - Accessory Return Management
Requirement 3 originally restored stock by exclusively invoking ProductService.restoreStock, 
which lacked handling for accessory inventory, leaving returned accessories with unrestored 
stock. This was caused by ReturnService and ReturnRepository being tightly bound only to 
standard products (Product) and ignoring accessories. The solution involved updating 
ReturnService to receive AccessoryService via constructor and delegate stock restoration 
according to the returned item type, adding a restoreStock method in AccessoryService that 
mirrors ProductService, and updating ReturnRepository to properly resolve references to 
accessories during data loading.

A5 - Discount-Aware Refund Calculation
Return.calculateRefundAmount originally summed the list prices of returned items, 
meaning that if the original sale included a promotion discount, the store over-refunded 
the customer compared to what they actually paid. This occurred because the refund logic 
failed to factor in the proportional discount applied during the original transaction. 
To correct this, Return.calculateRefundAmount was modified to compute each item's refund 
proportionally to the original sale's discount using the formula itemPrice * (1 - discountAmount / subtotal), 
and Return.generateReturnReceipt was updated to display the list price, proportional discount, 
and net refunded amount per item.

A6 - Monthly Balance Report Enhancement
Requirement 3 required displaying total sales, total returns, and net balance, 
but generateMonthlyBalance originally returned only the net balance, and with 
integrated promotions and warranties, sales totals needed to reflect the final 
sale amounts. This was caused by incomplete reporting logic that ignored gross 
sales, gross returns breakdown, and integrated pricing structures. The solution 
added calculateMonthlySales and calculateMonthlyReturns methods to ReturnService 
while preserving generateMonthlyBalance to return the net difference, ensuring 
the sales total correctly utilizes the final price of each sale, and updating 
ConsoleMenu to display all three values clearly in the monthly balance option.

A7 - Warranty Cancellation on Console Return
None of the individual requirements specified how to handle the warranty of a 
returned console, but in the integrated system, a returned console cannot maintain 
an active warranty. This situation was due to the absence of a rollback or cancellation 
mechanism for warranties associated with returned items. To address this, a cancelWarranties 
method was added to WarrantyService to remove the product's warranties for the specified 
sale and return the refundable cost (zero for basic warranties, the additional cost 
for extended warranties). Finally, ReturnService.registerReturn was updated to invoke 
this method for every returned console, integrating the returned value into the total 
refund amount and incorporating warranty refund amounts into the return calculation 
and receipt generation.