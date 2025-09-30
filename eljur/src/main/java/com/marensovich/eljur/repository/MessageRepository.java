package com.marensovich.eljur.repository;

import com.marensovich.eljur.model.Messages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Private message repository.
 */
@Repository
public interface MessageRepository extends JpaRepository<Messages, String> {


    List<Messages> getMessagesBySender_IdAndTarget_Id(Integer senderId, Integer targetId);

    Object getMessagesBySender_Id(Integer senderId);

    Object getMessagesById(Integer id);
}
