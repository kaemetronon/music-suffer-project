package com.music.suffer.user.controller;

import com.music.suffer.user.model.LoginRequest;
import com.music.suffer.user.model.TokenResponse;
import com.music.suffer.user.model.User;
import com.music.suffer.user.repository.UserRepository;
import com.music.suffer.user.service.TokenGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {
    private final TokenGenerator tokenGenerator;
    private final UserRepository userRepository;

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    @ResponseStatus(HttpStatus.OK)
    public TokenResponse login(@RequestBody LoginRequest request) {
        return tokenGenerator.generate(request);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/register")
    public User register(@RequestBody User user) {
        System.out.println(user);
        return userRepository.save(user);
    }
}
