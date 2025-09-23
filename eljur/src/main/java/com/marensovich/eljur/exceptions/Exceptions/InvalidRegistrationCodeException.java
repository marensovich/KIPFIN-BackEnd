package com.marensovich.eljur.exceptions.Exceptions;

/**
 * Thrown when a registration code is invalid or not recognized.
 *
 * @author marensovich
 * @version v.0.1
 * @since v.0.1
 */
public class InvalidRegistrationCodeException extends RuntimeException {

    /**
     * Instantiates a new Invalid registration code exception.
     *
     * @param message the error message
     */
    public InvalidRegistrationCodeException(String message) {
        super(message);
    }
}
