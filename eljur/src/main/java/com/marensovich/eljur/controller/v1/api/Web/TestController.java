package com.marensovich.eljur.controller.v1.api.Web;


import com.marensovich.eljur.data.system.PostTypes;
import com.marensovich.eljur.model.Groups;
import com.marensovich.eljur.model.User;
import com.marensovich.eljur.repository.UserRepository;
import net.datafaker.Faker;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * REST controller for testing DTO and another components of the project
 *
 * @author marensovich
 * @version v.0.1
 * @since v.0.1
 */
@RestController
@RequestMapping("/api/v1/test")
public class TestController {

    private final Faker faker = new Faker(new Locale("en"));
    private final Random random = new Random();

    private final UserRepository userRepository;

    public TestController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/getUsers")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = new ArrayList<>();
        for (User user : userRepository.findAll()) {
            users.add(user);
        }
        return ResponseEntity.ok(users);
    }

    @PostMapping("createUsers/{amount}")
    public ResponseEntity<List<User>> createUsers(@PathVariable Integer amount) {
        List<User> users = new ArrayList<>(100);
        PostTypes[] posts = PostTypes.values();

        for (int i = 0; i < amount; i++) {
            User user = new User();

            // username: sanitize + гарантированная уникальность (append index)
            String base = sanitize(faker.name().username()).toLowerCase();
            base = truncate(base, 24); // оставляем место для индекса
            String username = base + i; // должно быть <= 30
            user.setUsername(username);

            // email: делаем уникальный, привязанный к username (надёжно)
            user.setEmail(username + "@example.com");

            // пароль — для теста можно plain; если у тебя Spring Security, используй PasswordEncoder
            user.setPassword("password" + i);

            // ip и fullname/phone — урезаем до ограничений колонок
            user.setRegIP(truncate(faker.internet().ipV4Address(), 15));
            user.setLastIP(truncate(faker.internet().ipV4Address(), 15));
            user.setFullname(truncate(faker.name().fullName(), 45));
            user.setPhone(truncate(faker.phoneNumber().cellPhone(), 45));

            // случайный PostTypes
            user.setPost(posts[random.nextInt(posts.length)]);

            users.add(user);
        }

        try {
            userRepository.saveAll(users);
            System.out.println("✅ 100 пользователей добавлены в базу");
        } catch (Exception e) {
            System.err.println("Ошибка при сохранении пользователей:");
            e.printStackTrace();
        }
        return ResponseEntity.ok(users);
    }

    @PostMapping("/deleteUsers")
    public ResponseEntity<List<User>> deleteUsers() {
        userRepository.deleteAll();
        return ResponseEntity.ok(userRepository.findAll().stream().toList());
    }


    private static String sanitize(String s) {
        if (s == null) return "user";
        return s.replaceAll("[^A-Za-z0-9_\\-]", "");
    }

    private static String truncate(String s, int max) {
        if (s == null) return null;
        return s.length() <= max ? s : s.substring(0, max);
    }

}
