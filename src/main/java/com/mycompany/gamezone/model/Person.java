package com.mycompany.gamezone.model;

/**
 * Represents a generic person that interacts with the store, such as a client
 * or a vendor. This class is abstract because a person must always be
 * specialized into a specific role.
 *
 * @author Salomejimenez
 */
public abstract class Person {

    private int id;
    private String name;
    private String phone;

    /**
     * Creates a new Person with the given identification data.
     *
     * @param id the person's unique identifier
     * @param name the person's full name
     * @param phone the person's contact phone number
     *  @throws IllegalArgumentException if id is not greater than zero,
 *         or if name or phone are null or blank
     */
    public Person(int id, String name, String phone) {
        if (id <= 0) {
            throw new IllegalArgumentException("El id debe ser mayor a cero.");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }
        if (phone == null || phone.isBlank()) {
            throw new IllegalArgumentException("El teléfono es obligatorio.");
        }
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    /**
     * Returns the person's identifier.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the person's identifier.
     *
     * @param id the new id to assign
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the person's full name.
     *
     * @return the name
     */

    public String getName() {
        return name;
    }

    /**
     * Sets the person's full name.
     *
     * @param name the new name to assign
     */

    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * Returns the person's contact phone number.
     *
     * @return the phone
     */

    public String getPhone() {
        return phone;
    }

    /**
     *
     * Sets the person's contact phone number.
     *
     * @param phone the new phone number to assign
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    // Each subclass must specify the role it plays within the system (polymorphism)
    public abstract String describeRole();
}
