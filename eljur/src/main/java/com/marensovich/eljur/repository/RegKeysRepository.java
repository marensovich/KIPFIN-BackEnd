package com.marensovich.eljur.repository;

import com.marensovich.eljur.model.RegKeys;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * The interface Reg keys repository.
 */
@Repository
public interface RegKeysRepository extends JpaRepository<RegKeys, String> {
    /**
     * Find by status list.
     *
     * @param status the status
     * @return the list
     */
    List<RegKeys> findByStatus(String status);

    /**
     * Find by email list.
     *
     * @param email the email
     * @return the list
     */
    List<RegKeys> findByEmail(String email);

    /**
     * Find by registration key optional.
     *
     * @param registrationKey the registration key
     * @return the optional
     */
    Optional<RegKeys> findByRegistrationKey(String registrationKey);
}
