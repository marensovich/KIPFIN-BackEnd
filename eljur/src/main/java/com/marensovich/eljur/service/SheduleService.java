package com.marensovich.eljur.service;

import com.marensovich.eljur.data.ScoreWorkType;
import com.marensovich.eljur.model.Homework;
import com.marensovich.eljur.model.Shedule;
import com.marensovich.eljur.model.Students;
import com.marensovich.eljur.model.User;
import com.marensovich.eljur.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * The type Shedule service.
 */
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
    @Autowired
    private FilesRepository filesRepository;

    /**
     * Get lessons tree map.
     *
     * @param user      the user
     * @param startDate the start date
     * @param endDate   the end date
     * @return the tree map
     */
    public TreeMap<String, TreeMap<Integer, Map<String, Object>>> getLessons(User user,
                           String startDate,
                           String endDate){
        Students student = studentsRepository.getStudentsById(user.getId());

        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        LocalDateTime startOfWeek = LocalDateTime.from(startDate != null ? LocalDate.parse(startDate, formatter) : now.with(DayOfWeek.MONDAY));
        LocalDateTime endOfWeek = LocalDateTime.from(endDate != null ? LocalDate.parse(endDate, formatter) : now.with(DayOfWeek.SUNDAY));

        List<Shedule> lessons = lessonRepository.getLessonsByDateBetweenAndGroupAndSubgroupId(startOfWeek, endOfWeek, student.getGroup(), student.getSubgroup());
        TreeMap<String, TreeMap<Integer, Map<String, Object>>> groupedLessons = lessons.stream()
                .collect(Collectors.groupingBy(
                        lesson -> lesson.getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                        TreeMap::new,
                        Collectors.toMap(
                                Shedule::getRank,
                                lesson -> {
                                    Map<String, Object> lessonDetails = new HashMap<>();

                                    // Основная информация о уроке
                                    lessonDetails.put("subject", subjectRepository.getSubjectNameById(lesson.getSubject().getId()));
                                    lessonDetails.put("room", lesson.getRoom());
                                    lessonDetails.put("teacher", userRepository.getUserById(lesson.getTeacher().getId()).getFullname());
                                    lessonDetails.put("time", lesson.getTime());

                                    // Домашнее задание
                                    homeworkRepository.findHomeworkByLessonId(lesson.getId())
                                            .stream()
                                            .map(Homework::getText)
                                            .findFirst()
                                            .ifPresent(homework -> lessonDetails.put("homework", homework));

                                    // Файлы домашнего задания
                                    homeworkRepository.findHomeworkByLessonId(lesson.getId())
                                            .stream()
                                            .flatMap(hw -> hw.getFiles().stream())
                                            .map(file -> filesRepository.getFilenamesByFilename(file.getFilename())) // тут уже конкретный файл
                                            .collect(Collectors.collectingAndThen(Collectors.toList(), list -> {
                                                if (!list.isEmpty()) {
                                                    lessonDetails.put("homework_files", list);
                                                }
                                                return list;
                                            }));


                                    // Оценки
                                    List<String> scoreTypes = scoreRepository.getScoresByUser_IdAndSubject_Id(user.getId(), lesson.getId());
                                    List<Integer> scores = scoreTypes.stream()
                                            .map(scoreService::convertScoreTypeToInt)
                                            .collect(Collectors.toList());
                                    lessonDetails.put("scores", scores);

                                    // Типы работ
                                    List<String> scoreWorkTexts = scoreRepository.getScoresByUser_IdAndSubject_Id(user.getId(), lesson.getId())
                                            .stream()
                                            .map(scoreService::convertScoreWorkTypeToString) // и тут тоже строку
                                            .collect(Collectors.toList());
                                    lessonDetails.put("scoreWorkType", scoreWorkTexts);

                                    // Тексты оценок
                                    List<String> scoreTexts = scoreRepository.getAllByLessonIdAndUser_Id(lesson.getId(), user.getId());
                                    lessonDetails.put("score_texts", scoreTexts);

                                    return lessonDetails;
                                },
                                (existing, replacement) -> existing, // resolver для дубликатов ключей
                                TreeMap::new // supplier для TreeMap
                        )));
        return groupedLessons;
    }
}
