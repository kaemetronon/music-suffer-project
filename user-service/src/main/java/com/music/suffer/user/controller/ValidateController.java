package com.music.suffer.user.controller;

import com.music.suffer.user.model.User;
import com.music.suffer.user.service.TokenParser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class ValidateController {
    private final TokenParser tokenParser;

    @RequestMapping(method = RequestMethod.GET, value = "/validate")
    public boolean validateToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        return tokenParser.validate(authorization);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/user/me")
    public User userMe(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        return tokenParser.parse(authorization);
    }
}
