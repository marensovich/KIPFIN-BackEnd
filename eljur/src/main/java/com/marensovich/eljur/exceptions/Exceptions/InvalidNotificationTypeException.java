package com.marensovich.eljur.exceptions.Exceptions;

/**
 * Thrown when an unsupported notification type is provided.
 *
 * <p>Used in notification settings and delivery services.</p>
 *
 * @author marensovich
 * @version v.0.1
 * @since v.0.1
 */
public class InvalidNotificationTypeException extends RuntimeException {

    /**
     * Instantiates a new Invalid notification type exception.
     *
     * @param message the error message
     */
    public InvalidNotificationTypeException(String message) {
        super(message);
    }
}
