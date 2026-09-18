Return Module — Analysis
1. What kind of relationship is there between Return and Sale?

Association. Return just holds a reference to the Sale it came from (originalSale), 
nothing more. It's not inheritance, since a Return isn't a type of Sale. And it's 
not composition either, since the Sale doesn't depend on the Return to exist — 
the Sale is already saved before any Return is even created, and stays around no 
matter what happens to the Return.

2. How do we represent that a return can have only some of the products, not all?

Return has its own List<Product> returnedProducts, separate from the Sale's product 
list. It stores actual Product references (not copies, not just IDs), so the return 
only ever contains what was actually given back — could be 1 product, could be all 
of them.

3. Where does the 30-day rule live, and how do we check the date difference?

In Sale, as canBeReturned(). It's the sale's own condition to check ("am I still 
within the return window?"), so it belongs there rather than in Return. It uses 
dateTime.plusDays(30) and LocalDateTime.now().isAfter(...) to check if today already 
passed that limit.

4. Which existing method gets reused to restore stock, and where's it called from?

ProductService gets a new method, restoreStock(productId, quantity), which adds 
back the returned quantity (as opposed to updateStock, which just overwrites the 
stock number). ReturnService calls it right after validating the return, before 
saving it. Keeping this in ProductService matters because stock is its responsibility 
— if ReturnService touched stock on its own too, you'd have two places able to change 
the same value and they'd eventually get out of sync.

5. Where does the monthly balance report go, and what does it need?

In ReturnService. A balance report is really about returns' impact on sales, and 
ReturnService is already the class coordinating everything return-related, so it 
makes sense to put it there instead of creating a separate report class. It needs 
ReturnRepository (to pull returns for the given month/year) and SaleService (to pull 
sales for the same period) — both already injected in the class for other methods, 
so nothing new needs to be added.