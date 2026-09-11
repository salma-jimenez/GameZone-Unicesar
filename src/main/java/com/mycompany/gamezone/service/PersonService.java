
package com.mycompany.gamezone.service;

import com.mycompany.gamezone.model.Customer;
import com.mycompany.gamezone.model.Person;
import com.mycompany.gamezone.model.Seller;
import com.mycompany.gamezone.persistence.PersonRepository;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains the business rules related to customers and sellers.
 *
 * @author Salomejimenez
 */
public class PersonService {
    
    private final PersonRepository personRepository;

    /**
     * Creates a new PersonService using the given repository.
     *
     * @param personRepository the repository used to persist people
     */
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    /**
     * Registers a new customer, validating that the id is not already used.
     *
     * @param id    the customer's unique identifier
     * @param name  the customer's full name
     * @param phone the customer's contact phone number
     * @param email the customer's email address
     * @return the newly registered customer
     * @throws IllegalArgumentException if a person with the given id already exists
     */
    public Customer registerCustomer(int id, String name, String phone, String email) {
        if (idAlreadyExists(id)) {
            throw new IllegalArgumentException("Ya existe una persona registrada con ese id.");
        }
        Customer customer = new Customer(id, name, phone, email);
        List<Person> people = personRepository.load();
        people.add(customer);
        personRepository.save(people);
        return customer;
    }

    /**
     * Registers a new seller, validating that the id is not already used.
     *
     * @param id           the seller's unique identifier
     * @param name         the seller's full name
     * @param phone        the seller's contact phone number
     * @param employeeCode the seller's employee code
     * @param shift        the seller's assigned work shift
     * @return the newly registered seller
     * @throws IllegalArgumentException if a person with the given id already exists
     */
    public Seller registerSeller(int id, String name, String phone, int employeeCode, String shift) {
        if (idAlreadyExists(id)) {
            throw new IllegalArgumentException("Ya existe una persona registrada con ese id.");
        }
        Seller seller = new Seller(id, name, phone, employeeCode, shift);
        List<Person> people = personRepository.load();
        people.add(seller);
        personRepository.save(people);
        return seller;
    }

    /**
     * Checks whether a person with the given id is already registered.
     *
     * @param id the id to check
     * @return true if the id already exists, false otherwise
     */
    private boolean idAlreadyExists(int id) {
        List<Person> people = personRepository.load();
        for (Person p : people) {
            if (p.getId() == id) {
                return true;
            }
        }
        return false;
    }
    /**
     *  Returns the list of all registered customers.
     * 
     * @return  the list of customers
     */
    public List<Person> listCustomers() {
    List<Person> customers = new ArrayList<>();
    List<Person> allPeople = personRepository.load();
    for (Person p : allPeople) {
        if (p instanceof Customer) {
            customers.add(p);
        }
    }
    return customers;
}
    /**
     * Returns the list of all registered seller.
     * 
     * @return the list of sellers
     */
    public List<Person> listSellers() {
    List<Person> seller = new ArrayList<>();
    List<Person> allPeople = personRepository.load();
    for (Person p : allPeople) {
        if (p instanceof Seller) {
            seller.add(p);
        }
    }
    return seller;
}
    
}

