package com.marensovich.eljur.config.JWT;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;

/**
 * JWT Authentication Token.
 * <p>
 * Represents an authentication token for a user in Spring Security,
 * containing the user's ID and authentication state.
 * <p>
 * It is used for authorization checks and identifying the authenticated user
 * within the system.
 * </p>
 *
 * @author marensovich
 * @version v.0.1
 * @since v.0.1
 */
public class JwtAuthenticationToken implements Authentication {

    /**
     * User account ID.
     * <p>
     * Used as the principal for authentication.
     * </p>
     *
     * @since v.0.1
     */
    private final Integer userId;

    /**
     * Authentication state.
     * <p>
     * Indicates whether the user has been successfully authenticated.
     * </p>
     *
     * @since v.0.1
     */
    private boolean authenticated = true;

    /**
     * Creates a new JWT authentication token for the specified user.
     *
     * @param userId the user ID
     * @since v.0.1
     */
    public JwtAuthenticationToken(Integer userId) {
        this.userId = userId;
    }

    /**
     * Returns the authorities (roles/permissions) granted to the user.
     *
     * @return an empty collection, as roles are not used in this implementation
     * @since v.0.1
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    /**
     * Returns the credentials of the user.
     *
     * @return always {@code null}, as password is not stored in the token
     * @since v.0.1
     */
    @Override
    public Object getCredentials() {
        return null;
    }

    /**
     * Returns additional details about the authentication.
     *
     * @return always {@code null}, additional details are not used
     * @since v.0.1
     */
    @Override
    public Object getDetails() {
        return null;
    }

    /**
     * Returns the principal of the authentication token.
     *
     * @return the user ID
     * @since v.0.1
     */
    @Override
    public Integer getPrincipal() {
        return userId;
    }

    /**
     * Checks whether the user is authenticated.
     *
     * @return {@code true} if the user is authenticated
     * @since v.0.1
     */
    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    /**
     * Sets the authentication state of the user.
     *
     * @param isAuthenticated {@code true} if the user is authenticated
     * @throws IllegalArgumentException if the input value is invalid
     * @since v.0.1
     */
    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    /**
     * Returns the name of the user for this token.
     *
     * @return the string representation of the user ID
     * @since v.0.1
     */
    @Override
    public String getName() {
        return String.valueOf(userId);
    }
}
