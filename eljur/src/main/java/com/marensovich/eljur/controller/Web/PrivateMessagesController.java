package com.marensovich.eljur.controller.Web;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

import com.marensovich.eljur.exceptions.Exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.marensovich.eljur.config.JWT.JwtUtil;
import com.marensovich.eljur.model.PrivateMessage;
import com.marensovich.eljur.model.User;
import com.marensovich.eljur.repository.PrivateMessageRepository;
import com.marensovich.eljur.repository.UserRepository;

@RestController
@RequestMapping("/api/pm/")
public class PrivateMessagesController {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PrivateMessageRepository privateMessageRepository;


    @CrossOrigin(origins = "http://199.83.103.127:25323", allowCredentials = "true")
    @GetMapping("getUserPm")
    private ResponseEntity<?> getPM(@RequestParam String token, @RequestParam Integer offset) {
        Optional<User> user = userRepository.findById(jwtUtil.getUserIdFromToken(token));

        if (user.isEmpty()) throw new UserNotFoundException("Пользователь не найден");

        List<PrivateMessage> pm = privateMessageRepository.getPrivateMessagesById(user.get().getId());
        TreeMap<String, TreeMap<Integer, Map<String, Object>>> groupedMessages = pm.stream().collect(Collectors.groupingBy(
                privateMessage -> privateMessage.getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                TreeMap::new,
                Collectors.toMap(
                        PrivateMessage::getId,
                        privateMessage -> {
                            Map<String, Object> pmDetails = new HashMap<>();
                            pmDetails.put("fromID", privateMessageRepository.getFromIDById(privateMessage.getId()));
                            pmDetails.put("message", privateMessageRepository.getMessageById(privateMessage.getId()));
                            return pmDetails;
                        },
                        (existing, replacement) -> existing,
                        () -> new TreeMap<>()
                )
        ));
        return ResponseEntity.status(HttpStatus.OK).body(groupedMessages);
    }

}
