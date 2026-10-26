
package com.gamezone.exceptions;

/**
 * Exception thrown when input data does not meet format, range, or type criteria.
 * Examples include negative prices, negative quantities, or empty strings for required fields.
 */
public class InvalidDataException extends GameZoneException {

    /**
     * Constructs a new InvalidDataException with a formatted descriptive message.
     *
     * @param fieldName the name of the field that contains invalid data
     * @param problemDescription the description of why the data is invalid
     */
    public InvalidDataException(String fieldName, String problemDescription) {
        super("El campo '" + fieldName + "' no es válido: " + problemDescription, "INVALID_DATA");
    }
}