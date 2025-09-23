package com.marensovich.eljur.exceptions.Exceptions;

/**
 * Thrown when a registration code has already been activated and cannot be reused.
 *
 * <p>Used in registration-related operations to prevent re-activation of codes.</p>
 *
 * @author marensovich
 * @version v.0.1
 * @since v.0.1
 */
public class ActivatedRegistrationCodeException extends RuntimeException {

    /**
     * Instantiates a new Activated registration code exception.
     *
     * @param message the error message
     */
    public ActivatedRegistrationCodeException(String message) {
        super(message);
    }
}
