package com.marensovich.eljur.exceptions.Exceptions;

/**
 * Thrown when a registration code cannot be found in the system.
 *
 * @author marensovich
 * @version v.0.1
 * @since v.0.1
 */
public class RegCodeNotFound extends RuntimeException {

    /**
     * Instantiates a new Reg code not found exception.
     *
     * @param message the error message
     */
    public RegCodeNotFound(String message) {
        super(message);
    }
}
