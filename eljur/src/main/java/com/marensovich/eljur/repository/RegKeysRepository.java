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

    Optional<RegKeys> findByKey(String key);
}
