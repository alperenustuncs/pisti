package com.example.server.service;

import javax.mail.MessagingException;

public interface EmailService {
    void sendSimpleEmail(String toAddress, String subject, String message) throws MessagingException;
}
