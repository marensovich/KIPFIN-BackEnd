package com.marensovich.eljur.exceptions.Exceptions;

/**
 * Thrown when a provided password does not match requirements or is incorrect.
 *
 * @author marensovich
 * @version v.0.1
 * @since v.0.1
 */
public class InvalidPasswordException extends RuntimeException {

    /**
     * Instantiates a new Invalid password exception.
     *
     * @param message the error message
     */
    public InvalidPasswordException(String message) {
        super(message);
    }
}
