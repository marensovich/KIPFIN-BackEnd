package com.marensovich.eljur.repository;

import com.marensovich.eljur.model.RegKeys;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegKeysRepository extends JpaRepository<RegKeys, String> {
    List<RegKeys> findByStatus(String status);
    List<RegKeys> findByEmail(String email);
    Optional<RegKeys> findByRegistrationKey(String registrationKey);
}
