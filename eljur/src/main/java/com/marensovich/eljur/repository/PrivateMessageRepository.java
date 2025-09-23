package com.marensovich.eljur.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.marensovich.eljur.model.PrivateMessage;

/**
 * The interface Private message repository.
 */
@Repository
public interface PrivateMessageRepository extends JpaRepository<PrivateMessage, String> {


    /**
     * Gets private messages by id.
     *
     * @param id the id
     * @return the private messages by id
     */
    List<PrivateMessage> getPrivateMessagesById(Integer id);

    /**
     * Gets from id by id.
     *
     * @param id the id
     * @return the from id by id
     */
    Integer getFromIDById(Integer id);

    /**
     * Gets message by id.
     *
     * @param id the id
     * @return the message by id
     */
    String getMessageById(Integer id);


}
