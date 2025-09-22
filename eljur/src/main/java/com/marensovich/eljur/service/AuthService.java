package com.marensovich.eljur.service;

import com.marensovich.eljur.data.NotificationType;
import com.marensovich.eljur.exceptions.Exceptions.ActivatedRegistrationCodeException;
import com.marensovich.eljur.exceptions.Exceptions.InvalidRegistrationCodeException;
import com.marensovich.eljur.model.*;
import com.marensovich.eljur.repository.RegKeysRepository;
import com.marensovich.eljur.repository.StudentsRepository;
import com.marensovich.eljur.repository.TeacherRepository;
import com.marensovich.eljur.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

@Service
public class AuthService {

    @Autowired private RegKeysService regKeysService;
    @Autowired private RegKeysRepository regKeysRepository;
    @Autowired private StudentsRepository studentsRepository;
    @Autowired private TeacherRepository teacherRepository;
    @Autowired private UserRepository userRepository;

    //private PasswordEncoder passwordEncoder;


    public void registrationUser(String key, String login, String password, HttpServletRequest request) {
        String code = regKeysService.findRegistrationKey(key);
        String email = regKeysService.getEmailByRegistrationKey(key);
        RegKeys regKeys = regKeysRepository.findByRegistrationKey(key)
                .orElseThrow(() -> new RuntimeException("Код регистрации не найден."));
        if (code == null) {
            throw new InvalidRegistrationCodeException("Неправильно введен код регистрации");
        } else if (regKeysService.getStatusByRegistrationKey(key).equals("Activated")) {
            throw new ActivatedRegistrationCodeException("Данный код уже активирован.");
        }
        Timestamp timestamp = new Timestamp(new Date().getTime());
        regKeys.setEmail(email);
        regKeys.setStatus("Activated");
        regKeys.setActivatedAt(timestamp);
        String post = regKeys.getPost();
        User user = new User();
        user.setEmail(email);
        user.setPhone(regKeys.getPhone());
        user.setPassword(password);
        user.setUsername(login);
        user.setGroup(regKeys.getGroup());
        user.setFullname(regKeys.getFullName());
        user.setRegIP(request.getRemoteAddr());
        user.setPost(post);
        user.setLastJoinIP(request.getRemoteAddr());
        user.setNotificationType(NotificationType.Without_Notification);
        user.setNotificationHomework(false);
        user.setNotificationMessages(false);
        user.setNotificationNews(false);
        user.setNotificationScore(false);
        user.setBlack_theme(false);
        userRepository.save(user);
        switch (post) {
            case "student" -> {
                Students student = new Students();
                student.setFullname(regKeys.getFullName());
                student.setGroup(regKeys.getGroup());
                student.setSubgroup(regKeys.getSubgroup());
                student.setId(user.getId());
                studentsRepository.save(student);
            }
            case "teacher" -> {
                Teacher teacher = new Teacher();
                teacher.setId(user.getId());
                teacher.setTeacher_groupID(regKeys.getGroup());
                teacherRepository.save(teacher);
            }
            case "admin" -> {
                Admins admin = new Admins();
                admin.setId(user.getId());
            }
        }
    }

}
