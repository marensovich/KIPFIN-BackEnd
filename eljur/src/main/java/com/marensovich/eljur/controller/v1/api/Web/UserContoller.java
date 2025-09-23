package com.marensovich.eljur.controller.v1.api.Web;


import com.marensovich.eljur.config.JWT.JwtUtil;
import com.marensovich.eljur.exceptions.Exceptions.InvalidJwtTokenFormat;
import com.marensovich.eljur.exceptions.Exceptions.UserNotFoundException;
import com.marensovich.eljur.model.User;
import com.marensovich.eljur.repository.UserRepository;
import com.marensovich.eljur.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

/**
 * REST controller for managing user-related data.
 *
 * <p>This controller provides endpoints for:
 * <ul>
 *     <li>Getting username by token</li>
 *     <li>Getting user ID by username</li>
 *     <li>Fetching complete user profile information</li>
 * </ul>
 *
 * Requires JWT-based authentication for some endpoints.
 *
 * @author marensovich
 * @version v.0.1
 * @since v.0.1
 */
@RestController
@RequestMapping("/api/v1/users")
public class UserContoller {


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private ProfileService profileService;


    /**
     * Retrieves the username of a user by JWT token.
     *
     * @param token the JWT token
     * @return the response entity containing the username
     * @throws InvalidJwtTokenFormat if the token format is invalid
     * @throws UserNotFoundException if the user does not exist
     * @since v.0.1
     */
    @CrossOrigin(origins = "http://199.83.103.127:25323", allowCredentials = "true")
    @GetMapping("/getUsernameByToken")
    public ResponseEntity<?> getUsernameByToken(@RequestParam String token) {
        if (token.chars().filter(ch -> ch == '.').count() != 2) {
            throw new InvalidJwtTokenFormat("Invalid token format");
        }
        Integer userID = jwtUtil.getUserIdFromToken(token);
        Optional<User> userOptional = userRepository.findById(userID);
        if (userOptional.isPresent()) {
            String username = userOptional.get().getUsername();
            return ResponseEntity.ok().body(Map.of("username", username));
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

    /**
     * Retrieves the user ID by username.
     *
     * @param username the username
     * @return the response entity containing the user ID
     * @throws UserNotFoundException if the user does not exist
     * @since v.0.1
     */
    @CrossOrigin(origins = "http://199.83.103.127:25323", allowCredentials = "true")
    @GetMapping("/getIDbyUsername")
    public ResponseEntity<?> getIDbyUsername(@RequestParam String username) {
        User user = userRepository.findByUsername(username);

        if (user == null) throw new UserNotFoundException("User not found");

        return ResponseEntity.ok().body(Map.of("username", user.getUsername()));
    }

    /**
     * Retrieves full user profile information by user ID.
     *
     * @param id the user ID
     * @return the response entity containing profile information
     * @throws UserNotFoundException if the user does not exist
     * @since v.0.1
     */
    @CrossOrigin(origins = "http://199.83.103.127:25323", allowCredentials = "true")
    @GetMapping("/getAllInfo")
    public ResponseEntity<?> getAllUserInfo(@RequestParam Integer id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) throw new UserNotFoundException("User not found");

        Map profileInfo = profileService.getProfileInfo(user);

        return ResponseEntity.status(HttpStatus.OK).body(profileInfo);
    }

}



