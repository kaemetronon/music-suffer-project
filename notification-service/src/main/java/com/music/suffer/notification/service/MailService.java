package com.music.suffer.notification.service;

public interface MailService {
    void send(String email, String subject, String message);
}
