
package com.gamezone.exceptions;

/**
 * Root exception class for all custom GameZone exceptions.
 * Extends RuntimeException to avoid forcing checked exception handling across the system.
 */
public class GameZoneException extends RuntimeException {
    
    private String errorCode;

    /**
     * Constructs a new GameZoneException with the specified message.
     * 
     * @param message the detail message
     */
    public GameZoneException(String message) {
        super(message);
    }

    /**
     * Constructs a new GameZoneException with the specified message and error code.
     * 
     * @param message the detail message
     * @param errorCode specific code identifying the error type
     */
    public GameZoneException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    /**
     * Gets the error code associated with this exception.
     * 
     * @return the error code
     */
    public String getErrorCode() {
        return errorCode;
    }
}