package com.marensovich.eljur.service;

import com.marensovich.eljur.data.AdminPosts;
import com.marensovich.eljur.data.system.PostTypes;
import com.marensovich.eljur.data.system.RegKeysStatus;
import com.marensovich.eljur.exceptions.Exceptions.ActivatedRegistrationCodeException;
import com.marensovich.eljur.exceptions.Exceptions.InvalidRegistrationCodeException;
import com.marensovich.eljur.exceptions.Exceptions.RegCodeNotFound;
import com.marensovich.eljur.model.*;
import com.marensovich.eljur.repository.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;

/**
 * The type Auth service.
 */
@Service
public class AuthService {

    @Autowired private RegKeysService regKeysService;
    @Autowired private RegKeysRepository regKeysRepository;
    @Autowired private StudentsRepository studentsRepository;
    @Autowired private TeacherRepository teacherRepository;
    @Autowired private UserRepository userRepository;
    @Autowired
    private GroupsRepository groupsRepository;
    @Autowired
    private AdminRepostory adminRepostory;

    //private PasswordEncoder passwordEncoder;


    /**
     * Registration user.
     *
     * @param key      the key
     * @param login    the login
     * @param password the password
     * @param request  the request
     */
    public void registrationUser(String key, String login, String password, HttpServletRequest request) {
        //todo: update method, add getting code and email by regKeys
        String code = regKeysService.findRegistrationKey(key);
        String email = regKeysService.getEmailByRegistrationKey(key);
        RegKeys regKeys = regKeysRepository.findByKey(key)
                .orElseThrow(() -> new RegCodeNotFound("Registration code not found."));

        if (code == null) {
            throw new InvalidRegistrationCodeException("Invalid registration code.");
        } else if (regKeysService.getStatusByRegistrationKey(key).equals("Activated")) {
            throw new ActivatedRegistrationCodeException("Registration code already activated.");
        }
        Timestamp timestamp = new Timestamp(new Date().getTime());
        regKeys.setEmail(email);
        regKeys.setStatus(RegKeysStatus.USED);
        regKeys.setActivateAt(LocalDateTime.now());
        PostTypes post = regKeys.getPost();
        User user = new User();
        user.setEmail(email);
        user.setPhone(regKeys.getPhone());
        user.setPassword(password);
        user.setUsername(login);
        user.setGroup(regKeys.getGroup());
        user.setFullname(regKeys.getFullname());
        user.setRegIP(request.getRemoteAddr());
        user.setPost(post);
        user.setLastIP(request.getRemoteAddr());
        userRepository.save(user);
        switch (post) {
            case student -> {
                Students student = new Students();
                student.setFullname(regKeys.getFullname());
                student.setGroup(regKeys.getGroup());
                student.setSubgroup(regKeys.getSubgroup());
                student.setId(user.getId());
                studentsRepository.save(student);
            }
            case teacher -> {
                Teacher teacher = new Teacher();
                teacher.setId(user.getId());
                teacher.setGroup(regKeys.getGroup());
                teacherRepository.save(teacher);
            }
            case admin -> {
                Admins admin = new Admins();
                admin.setId(user.getId());
                admin.setPost(AdminPosts.DIRECTOR);
                adminRepostory.save(admin);
            }
        }
    }


    public void test(){
        User user = new User();
        user.setUsername("eljur");
        user.setPassword("eljur");
        user.setEmail("eljur@eljur.com");
        user.setRegIP("0.0.0.0");
        user.setFullname("Eljur");
        user.setPhone("+1234567890");
        user.setTelegramId(123123132L);
        user.setPost(PostTypes.admin);
        user.setGroup(groupsRepository.getGroupsById(1));
        user.setLastIP("127.0.0.1");

        userRepository.save(user);
    }

}
