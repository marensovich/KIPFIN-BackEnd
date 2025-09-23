package com.marensovich.eljur.config.JWT;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * The type Jwt authentication filter.
 * @author marensovich
 * @since v.0.1
 * @version v.0.1
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    /**
     * Instantiates a new Jwt authentication filter.
     * @since v.0.1
     * @param jwtUtil the jwt util
     */
    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = extractToken(request);

        if (token != null && jwtUtil.validateToken(token)) {
            Integer userId = jwtUtil.getUserIdFromToken(token); // Извлекаем userId из токена
            JwtAuthenticationToken authenticationToken = new JwtAuthenticationToken(userId);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken); // Устанавливаем аутентификацию
        }

        filterChain.doFilter(request, response);
    }


    /**
     * Method to extract cookie or token from header
     * @since v.0.1
     * @param request Request to server
     * @return cookie or token from header
     */
    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }
}