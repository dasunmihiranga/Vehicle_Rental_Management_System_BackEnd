package edu.icet.service;

import jakarta.mail.MessagingException;

public interface EmailService {
    void sendAccountCreatedEmail(String toEmail, String username) throws Exception;
    void sendBookingResponseEmail(String toEmail,String username,String status) throws MessagingException;
}
