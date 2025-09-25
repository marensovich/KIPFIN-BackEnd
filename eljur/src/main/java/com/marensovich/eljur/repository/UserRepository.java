package com.marensovich.eljur.repository;


import com.marensovich.eljur.model.User;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The interface User repository.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User getUserById(Integer id);

    User findUserByUsername(String username);

    User getUserByUsername(String username);
}