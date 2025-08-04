package com.marensovich.eljur.service;


import com.marensovich.eljur.model.RegKeys;
import com.marensovich.eljur.repository.RegKeysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RegKeysService {

    @Autowired
    private RegKeysRepository regKeysRepository;


    public RegKeys createRegKey(RegKeys regKey) {
        return regKeysRepository.save(regKey);
    }

    public Optional<RegKeys> getRegKeyById(String id) {
        return regKeysRepository.findById(id);
    }

    public List<RegKeys> getAllRegKeys() {
        return regKeysRepository.findAll();
    }

    public void deleteRegKey(String id) {
        regKeysRepository.deleteById(id);
    }

    public String findRegistrationKey(String registrationKey) {
        Optional<RegKeys> regKeysOptional = regKeysRepository.findByRegistrationKey(registrationKey);
        return regKeysOptional.map(RegKeys::getRegistrationKey).orElse(null);
    }

    public String getEmailByRegistrationKey(String registrationKey) {
        Optional<RegKeys> regKeyOptional = regKeysRepository.findByRegistrationKey(registrationKey);
        return regKeyOptional.map(RegKeys::getEmail).orElse(null);
    }

    public String getStatusByRegistrationKey(String registrationKey) {
        Optional<RegKeys> regKeyOptional = regKeysRepository.findByRegistrationKey(registrationKey);
        return regKeyOptional.map(RegKeys::getStatus).orElse(null);
    }

}
