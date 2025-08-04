package com.marensovich.eljur.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.marensovich.eljur.model.PrivateMessage;

@Repository
public interface PrivateMessageRepository extends JpaRepository<PrivateMessage, String> {


    List<PrivateMessage> getPrivateMessagesById(Integer id);
    Integer getFromIDById(Integer id);
    String getMessageById(Integer id);


}
