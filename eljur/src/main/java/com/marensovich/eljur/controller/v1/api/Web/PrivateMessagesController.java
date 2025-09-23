package com.marensovich.eljur.controller.v1.api.Web;

import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import com.marensovich.eljur.exceptions.Exceptions.UserNotFoundException;
import com.marensovich.eljur.service.PrivateMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.marensovich.eljur.config.JWT.JwtUtil;
import com.marensovich.eljur.model.User;
import com.marensovich.eljur.repository.UserRepository;

/**
 * REST controller for private messaging.
 *
 * <p>This controller provides endpoints for:
 * <ul>
 *     <li>Fetching private messages of a user</li>
 * </ul>
 *
 * Authentication is handled via JWT token.
 *
 * @author marensovich
 * @version v.0.1
 * @since v.0.1
 */
@RestController
@RequestMapping("/api/v1/pm/")
public class PrivateMessagesController {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PrivateMessageService privateMessageService;

    /**
     * Retrieves private messages for a user identified by JWT token.
     *
     * @param token the JWT token of the authenticated user
     * @return the response entity with grouped messages or error message
     * @throws UserNotFoundException if the user does not exist
     * @since v.0.1
     */
    @CrossOrigin(origins = "http://199.83.103.127:25323", allowCredentials = "true")
    @GetMapping("getUserPrivateMessage")
    private ResponseEntity<?> getUserPrivateMessage(@RequestParam String token) {
        Optional<User> user = userRepository.findById(jwtUtil.getUserIdFromToken(token));

        if (user.isEmpty()) throw new UserNotFoundException("User not found!");

        try {
            TreeMap<String, TreeMap<Integer, Map<String, Object>>> groupedMessages = privateMessageService.getPrivateMessages(user);
            return ResponseEntity.status(HttpStatus.OK).body(groupedMessages);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Error while getting PM"));
        }

    }

}
