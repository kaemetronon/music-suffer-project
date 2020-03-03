package com.music.suffer.library.controller;

import com.music.suffer.library.model.Role;
import com.music.suffer.library.model.User;
import com.music.suffer.library.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasAuthority('ADMIN')")
@RequiredArgsConstructor
public class AdminController {
    private final UserRepository userRepository;

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public List<User> userList() {
        return userRepository.findAll();
    }

    @GetMapping("{user}")
    public User userPage(@PathVariable User user) {
        return user;
    }

    //тут я немного не понял. Если мы сохраняем юзера,
    // как мы его можем найи в базе, если его еще не существует в принципе?
    @PostMapping
    public User userSave(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam Map<String, String> form,
            @RequestParam("userId") User user
    ) {
        user.setUsername(username);
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());
        user.getRoles().clear();
        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }
        userRepository.save(user);
        return user;
    }
}
