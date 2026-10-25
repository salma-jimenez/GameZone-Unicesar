
package com.gamezone.exceptions;

/**
 * Exception thrown when a requested resource cannot be found in the system by its identifier.
 */
public class ResourceNotFoundException extends GameZoneException {

    /**
     * Constructs a new ResourceNotFoundException with a formatted descriptive message.
     *
     * @param resourceType the type of the resource (e.g., "producto", "cliente", "vendedor")
     * @param identifier the ID that was searched and not found
     */
    public ResourceNotFoundException(String resourceType, String identifier) {
        super("El " + resourceType + " con identificador " + identifier + " no fue encontrado", "NOT_FOUND");
    }
}
