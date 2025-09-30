package com.marensovich.eljur.service;

import com.marensovich.eljur.model.Subject;
import com.marensovich.eljur.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * The type Subject service.
 */
@Service
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    /**
     * Save subject subject.
     *
     * @param subject the subject
     * @return the subject
     */
// Method to save a new subject
    public Subject saveSubject(Subject subject) {
        return subjectRepository.save(subject);
    }

    /**
     * Find subject by id optional.
     *
     * @param id the id
     * @return the optional
     */
// Method to find a subject by its ID
    public Optional<Subject> findSubjectById(String id) {
        return subjectRepository.findById(id);
    }

    /**
     * Find subject by name optional.
     *
     * @param subjectName the subject name
     * @return the optional
     */
// Method to find a subject by its name
    public Optional<Subject> findSubjectByName(String subjectName) {
        return subjectRepository.findByName(subjectName);
    }

    /**
     * Gets all subjects.
     *
     * @return the all subjects
     */
// Method to get all subjects
    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    /**
     * Delete subject by id.
     *
     * @param id the id
     */
// Method to delete a subject by its ID
    public void deleteSubjectById(String id) {
        subjectRepository.deleteById(id);
    }

    /**
     * Delete all subjects.
     */
// Method to delete all subjects
    public void deleteAllSubjects() {
        subjectRepository.deleteAll();
    }
}