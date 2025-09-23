package com.marensovich.eljur.service;


import com.marensovich.eljur.model.RegKeys;
import com.marensovich.eljur.repository.RegKeysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * The type Reg keys service.
 */
@Service
public class RegKeysService {

    @Autowired
    private RegKeysRepository regKeysRepository;


    /**
     * Create reg key reg keys.
     *
     * @param regKey the reg key
     * @return the reg keys
     */
    public RegKeys createRegKey(RegKeys regKey) {
        return regKeysRepository.save(regKey);
    }

    /**
     * Gets reg key by id.
     *
     * @param id the id
     * @return the reg key by id
     */
    public Optional<RegKeys> getRegKeyById(String id) {
        return regKeysRepository.findById(id);
    }

    /**
     * Gets all reg keys.
     *
     * @return the all reg keys
     */
    public List<RegKeys> getAllRegKeys() {
        return regKeysRepository.findAll();
    }

    /**
     * Delete reg key.
     *
     * @param id the id
     */
    public void deleteRegKey(String id) {
        regKeysRepository.deleteById(id);
    }

    /**
     * Find registration key string.
     *
     * @param registrationKey the registration key
     * @return the string
     */
    public String findRegistrationKey(String registrationKey) {
        Optional<RegKeys> regKeysOptional = regKeysRepository.findByRegistrationKey(registrationKey);
        return regKeysOptional.map(RegKeys::getRegistrationKey).orElse(null);
    }

    /**
     * Gets email by registration key.
     *
     * @param registrationKey the registration key
     * @return the email by registration key
     */
    public String getEmailByRegistrationKey(String registrationKey) {
        Optional<RegKeys> regKeyOptional = regKeysRepository.findByRegistrationKey(registrationKey);
        return regKeyOptional.map(RegKeys::getEmail).orElse(null);
    }

    /**
     * Gets status by registration key.
     *
     * @param registrationKey the registration key
     * @return the status by registration key
     */
    public String getStatusByRegistrationKey(String registrationKey) {
        Optional<RegKeys> regKeyOptional = regKeysRepository.findByRegistrationKey(registrationKey);
        return regKeyOptional.map(RegKeys::getStatus).orElse(null);
    }

}
