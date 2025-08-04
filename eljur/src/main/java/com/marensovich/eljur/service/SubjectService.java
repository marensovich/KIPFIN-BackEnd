package com.marensovich.eljur.service;

import com.marensovich.eljur.model.Subject;
import com.marensovich.eljur.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    // Method to save a new subject
    public Subject saveSubject(Subject subject) {
        return subjectRepository.save(subject);
    }

    // Method to find a subject by its ID
    public Optional<Subject> findSubjectById(String id) {
        return subjectRepository.findById(id);
    }

    // Method to find a subject by its name
    public Optional<Subject> findSubjectByName(String subjectName) {
        return subjectRepository.findBySubjectName(subjectName);
    }

    // Method to get all subjects
    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    // Method to delete a subject by its ID
    public void deleteSubjectById(String id) {
        subjectRepository.deleteById(id);
    }

    // Method to delete all subjects
    public void deleteAllSubjects() {
        subjectRepository.deleteAll();
    }
}