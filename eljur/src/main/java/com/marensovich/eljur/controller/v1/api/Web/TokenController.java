package com.marensovich.eljur.controller.v1.api.Web;



import com.marensovich.eljur.config.JWT.JwtUtil;
import com.marensovich.eljur.exceptions.Exceptions.InvalidTokenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing JWT tokens.
 *
 * <p>This controller provides endpoints for:
 * <ul>
 *     <li>Validating user tokens</li>
 * </ul>
 *
 * Used primarily for authentication checks in the system.
 *
 * @author marensovich
 * @version v.0.1
 * @since v.0.1
 */
@RestController
@RequestMapping("/api/v1/token")
public class TokenController {

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Validates the provided JWT token.
     *
     * @param token the JWT token to validate
     * @return the response entity with validation result
     * @throws InvalidTokenException if the token is invalid
     * @since v.0.1
     */
    @CrossOrigin(origins = "http://199.83.103.127:25323", allowCredentials = "true")
    @GetMapping("/validateToken")
    public ResponseEntity<?> validateToken(@RequestParam String token) {
        if (jwtUtil.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.OK).body("Token is valid");
        } else {
            throw new InvalidTokenException("Invalid Token");
        }
    }
}
