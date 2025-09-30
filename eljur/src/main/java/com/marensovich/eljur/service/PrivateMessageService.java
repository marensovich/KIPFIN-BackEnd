package com.marensovich.eljur.service;

import com.marensovich.eljur.model.Messages;
import com.marensovich.eljur.model.User;
import com.marensovich.eljur.repository.MessageRepository;
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
    private MessageRepository messageRepository;

    /**
     * Gets private messages.
     *
     * @param user the user
     * @return the private messages
     */
    public TreeMap<String, TreeMap<Integer, Map<String, Object>>> getPrivateMessages(Optional<User> user) {
        List<Messages> pm = messageRepository.getMessagesBySender_IdAndTarget_Id(user.get().getId(), user.get().getId());
        return pm.stream().collect(Collectors.groupingBy(
                messages -> messages.getTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                TreeMap::new,
                Collectors.toMap(
                        Messages::getId,
                        messages -> {
                            Map<String, Object> pmDetails = new HashMap<>();
                            pmDetails.put("fromID", messageRepository.getMessagesBySender_Id(messages.getId()));
                            pmDetails.put("message", messageRepository.getMessagesById(messages.getId()));
                            return pmDetails;
                        },
                        (existing, replacement) -> existing,
                        TreeMap::new
                )
        ));
    }
}
