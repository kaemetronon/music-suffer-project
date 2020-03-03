package com.music.suffer.library.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class TestController {

    @GetMapping("/test")
    public ResponseEntity<String> test(HttpServletRequest request) {
        return ResponseEntity.ok(request.getHeader("Authorization"));
    }
}
