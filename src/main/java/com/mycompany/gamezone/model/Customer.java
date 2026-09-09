
package com.mycompany.gamezone.model;

import java.util.List;

/**
 * 
 * Represents a client of the store, who can purchase products
 * and has a record of previous sales.
 *
 * @author Salomejimenez
 */
public class Customer extends Person{
    
     private String email;
     private List<Sale> purchaseHistory;

   /**
    * Creates a new Client with the given identification and contact data.
    * 
    * @param id the person's unique identifier
    * @param name the person's full name
    * @param email the person's contact phone number
    * @param phone the client's email address
    */
    public Customer(int id, String name, String email, String phone) {
        super(id, name, phone);
        this.email = email;
        
    }
    /**
     * Returns the client's email address.
     * 
     * @return the email
     */
    public String getEmail() {
        return email;
    }
    /**
     * Sets the client's email address.
     * 
     * @param email the new email to assign
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * Returns role-specific information about this client,
     * including email and number of purchases made.
     * 
     * @return the role-specific description
     */

    @Override
    public String describeRole() {
        return ("Cliente: " + this.getName() + "\nEmail: " + this.email
            + "\nTeléfono: " + this.getPhone()
            + "\nCompras realizadas: " + this.purchaseHistory.size());
    }
     
    
}
