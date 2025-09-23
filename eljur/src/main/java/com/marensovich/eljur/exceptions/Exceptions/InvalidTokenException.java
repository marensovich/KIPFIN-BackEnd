package com.marensovich.eljur.exceptions.Exceptions;

/**
 * Thrown when a provided JWT token is invalid.
 *
 * @author marensovich
 * @version v.0.1
 * @since v.0.1
 */
public class InvalidTokenException extends RuntimeException {

    /**
     * Instantiates a new Invalid token exception.
     *
     * @param message the error message
     */
    public InvalidTokenException(String message) {
        super(message);
    }
}
