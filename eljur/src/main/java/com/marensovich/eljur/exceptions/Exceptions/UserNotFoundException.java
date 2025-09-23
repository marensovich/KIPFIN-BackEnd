package com.marensovich.eljur.exceptions.Exceptions;

/**
 * Thrown when a user is not found in the system.
 *
 * @author marensovich
 * @version v.0.1
 * @since v.0.1
 */
public class UserNotFoundException extends RuntimeException {

    /**
     * Instantiates a new User not found exception.
     *
     * @param message the error message
     */
    public UserNotFoundException(String message) {
        super(message);
    }
}
