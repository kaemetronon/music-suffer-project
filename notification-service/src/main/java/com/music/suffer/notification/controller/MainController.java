package com.music.suffer.notification.controller;

import com.music.suffer.notification.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sendMail")
public class MainController {

    private final MailService mailService;

    @Autowired
    public MainController(MailService ms) {
        mailService = ms;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public String send(String email, String subject, String message) {
        mailService.send(email, subject, message);
        return null;
    }


}
