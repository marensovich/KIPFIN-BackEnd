package com.marensovich.eljur.service;

import com.marensovich.eljur.model.PrivateMessage;
import com.marensovich.eljur.model.User;
import com.marensovich.eljur.repository.PrivateMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The type Private message service.
 */
@Service
public class PrivateMessageService {

    @Autowired
    private PrivateMessageRepository privateMessageRepository;

    /**
     * Gets private messages.
     *
     * @param user the user
     * @return the private messages
     */
    public TreeMap<String, TreeMap<Integer, Map<String, Object>>> getPrivateMessages(Optional<User> user) {
        List<PrivateMessage> pm = privateMessageRepository.getPrivateMessagesById(user.get().getId());
        return pm.stream().collect(Collectors.groupingBy(
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
                        TreeMap::new
                )
        ));
    }
}
