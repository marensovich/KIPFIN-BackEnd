package com.marensovich.eljur.repository;


import com.marensovich.eljur.model.User;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String user_username);

    @Query(value = "SELECT * FROM user WHERE user_id = :id", nativeQuery = true)
    Optional<User> findById(Integer id);

    User findByFullname(String fullname);

    @Query(value = "SELECT user_full_name FROM user WHERE user_id = :teacherID", nativeQuery = true)
    String getFullnameByUser_id(@Param("teacherID") Integer teacherID);


    @Query(value = "SELECT * FROM user WHERE user_username = :username", nativeQuery = true)
    User getByUsernameMobile(@Param("username") String username);
}