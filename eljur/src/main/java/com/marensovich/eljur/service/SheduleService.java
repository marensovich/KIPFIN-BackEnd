package com.marensovich.eljur.service;

import com.marensovich.eljur.model.Homework;
import com.marensovich.eljur.model.Shedule;
import com.marensovich.eljur.model.Students;
import com.marensovich.eljur.model.User;
import com.marensovich.eljur.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
public class SheduleService {

    @Autowired
    private ScheduleRepository lessonRepository;
    @Autowired
    private StudentsRepository studentsRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HomeworkRepository homeworkRepository;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private ScoreRepository scoreRepository;
    @Autowired
    private ScoreService scoreService;
    @Autowired
    private FileService fileService;

    public TreeMap<String, TreeMap<Integer, Map<String, Object>>> getLessons(User user,
                           String startDate,
                           String endDate){
        Students student = studentsRepository.getById(user.getId());

        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        LocalDate startOfWeek = startDate != null ? LocalDate.parse(startDate, formatter) : now.with(DayOfWeek.MONDAY);
        LocalDate endOfWeek = endDate != null ? LocalDate.parse(endDate, formatter) : now.with(DayOfWeek.SUNDAY);

        List<Shedule> lessons = lessonRepository.getLessonsByDateBetweenAndGroupAndSubgroup(startOfWeek, endOfWeek, student.getGroup(), student.getSubgroup());
        TreeMap<String, TreeMap<Integer, Map<String, Object>>> groupedLessons = lessons.stream()
                .collect(Collectors.groupingBy(
                        lesson -> lesson.getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                        TreeMap::new,
                        Collectors.toMap(
                                Shedule::getRank,
                                lesson -> {
                                    Map<String, Object> lessonDetails = new HashMap<>();
                                    lessonDetails.put("subject", subjectRepository.getSubjectNameById(lesson.getSubject()));
                                    lessonDetails.put("room", lesson.getRoom());
                                    lessonDetails.put("teacher", userRepository.getFullnameByUser_id(lesson.getTeacher()));
                                    homeworkRepository.findHomeworkByLessonID(lesson.getId())
                                            .map(Homework::getHomework)
                                            .ifPresent(homework -> lessonDetails.put("homework", homework));
                                    lessonDetails.put("time", lesson.getTime());
                                    homeworkRepository.getHomeworkFilesByLessonID(lesson.getId())
                                            .stream()
                                            .map(fileService::getFileNameWithID)
                                            .filter(homeworkFiles -> !homeworkFiles.isEmpty())
                                            .findFirst()
                                            .ifPresent(homeworkFiles -> lessonDetails.put("homework_files", homeworkFiles));

                                    List<String> score = scoreRepository.getScoreTypeByLessonIDAndUserId(lesson.getId(), user.getId());
                                    List<Integer> scores = score.stream()
                                            .map(scoreService::convertScoreTypeToInt)
                                            .toList();
                                    lessonDetails.put("scores", scores);

                                    List<String> scoreWork = scoreRepository.getScoreWorkTypeByLessonIDAndUserId(lesson.getId(), user.getId());
                                    List<String> scoreWorkTexts = scoreWork.stream()
                                            .map(scoreService::convertScoreWorkTypeToString)
                                            .toList();
                                    lessonDetails.put("scoreWorkType", scoreWorkTexts);

                                    List<String> scoreText = scoreRepository.getScoreTextByLessonIDAndUserId(lesson.getId(), user.getId());
                                    lessonDetails.put("score_texts", scoreText);

                                    return lessonDetails;
                                },
                                (existing, replacement) -> existing,
                                () -> new TreeMap<>()
                        )
                ));

        return groupedLessons;
    }
}
