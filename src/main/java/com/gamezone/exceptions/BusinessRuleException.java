
package com.gamezone.exceptions;

/**
 * Exception thrown when an operation violates a core business rule.
 * Examples include insufficient stock, duplicate customers, or invalid sale states.
 */
public class BusinessRuleException extends GameZoneException {

    /**
     * Constructs a new BusinessRuleException with a specific message detailing the violation.
     *
     * @param message the description of the business rule that was violated
     */
    public BusinessRuleException(String message) {
        super(message, "BUSINESS_RULE_ERROR");
    }
}