
package com.gamezone.validation;

import com.gamezone.exceptions.BusinessRuleException;
import com.gamezone.exceptions.InvalidDataException;
import com.gamezone.exceptions.ResourceNotFoundException;
import com.gamezone.model.Person;
import java.util.List;

/**
 * Validator class for Person-related operations.
 * Centralizes business rules and data format validations for the person module.
 */
public class PersonValidator {

    /**
     * Validates the basic data for a person.
     * 
     * @param id the person identifier
     * @param name the person's name
     * @param email the person's email
     * @param phone the person's phone
     */
    public static void validatePersonData(String id, String name, String email, String phone) {
        if (id == null || id.trim().isEmpty()) {
            throw new InvalidDataException("id", "El identificador no puede ser nulo ni estar vacío.");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidDataException("name", "El nombre no puede ser nulo ni estar vacío.");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new InvalidDataException("email", "El correo electrónico no puede estar vacío.");
        }
        if (!email.contains("@") || !email.contains(".")) {
            throw new InvalidDataException("email", "El correo electrónico debe tener un formato válido (contener '@' y '.').");
        }
        if (phone == null || phone.trim().isEmpty()) {
            throw new InvalidDataException("phone", "El teléfono no puede ser nulo ni estar vacío.");
        }
    }

    /**
     * Validates that a person object exists (is not null).
     * 
     * @param person the person instance returned by the repository
     * @param personId the identifier used to search for the person
     */
    public static void validatePersonExists(Person person, String personId) {
        if (person == null) {
            throw new ResourceNotFoundException("persona", personId);
        }
    }

    /**
     * Validates that a new person's ID does not already exist in the system.
     * 
     * @param existing the list of existing persons
     * @param newId the identifier of the new person being created
     */
    public static void validateUniquePerson(List<Person> existing, String newId) {
        if (existing != null) {
            for (Person p : existing) {
                if (p.getId() != null && p.getId().equals(newId)) {
                    throw new BusinessRuleException("Ya existe una persona registrada con el identificador: " + newId);
                }
            }
        }
    }
}
