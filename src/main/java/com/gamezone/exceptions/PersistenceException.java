
package com.gamezone.exceptions;

/**
 * Exception thrown when a file read or write operation fails.
 * Examples include corrupt files, denied permissions, or missing files.
 */
public class PersistenceException extends GameZoneException {

    /**
     * Constructs a new PersistenceException with a specific message and the original cause.
     *
     * @param message the detail message containing the context (file and operation)
     * @param cause the original exception (e.g., IOException) to maintain traceability
     */
    public PersistenceException(String message, Throwable cause) {
        super(message, "PERSISTENCE_ERROR");
        this.initCause(cause);
    }
}
