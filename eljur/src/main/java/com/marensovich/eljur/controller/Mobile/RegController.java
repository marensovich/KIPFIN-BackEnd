package com.marensovich.eljur.controller.Mobile;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

import com.marensovich.eljur.exceptions.Exceptions.ActivatedRegistrationCodeException;
import com.marensovich.eljur.exceptions.Exceptions.InvalidRegistrationCodeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.marensovich.eljur.data.NotificationType;
import com.marensovich.eljur.model.Admins;
import com.marensovich.eljur.model.RegKeys;
import com.marensovich.eljur.model.Students;
import com.marensovich.eljur.model.Teacher;
import com.marensovich.eljur.model.User;
import com.marensovich.eljur.repository.RegKeysRepository;
import com.marensovich.eljur.repository.StudentsRepository;
import com.marensovich.eljur.repository.TeacherRepository;
import com.marensovich.eljur.repository.UserRepository;
import com.marensovich.eljur.service.RegKeysService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@Controller("mobileRegController")
@RequestMapping("/api/mobile/auth")
public class RegController {


    @Autowired
    private RegKeysService regKeysService;

    @Autowired
    private RegKeysRepository regKeysRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private StudentsRepository studentsRepository;


    @CrossOrigin(origins = "http://199.83.103.127:25323", allowCredentials = "true")
    @GetMapping("/register")
    public ResponseEntity<?> register(@RequestParam String key, @RequestParam String login, @RequestParam String password, HttpServletRequest request) {
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
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Вы успешно прошли регистрацию."));
    }


}
