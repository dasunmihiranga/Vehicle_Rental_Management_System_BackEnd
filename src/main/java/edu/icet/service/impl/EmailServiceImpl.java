package edu.icet.service.impl;

import edu.icet.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Override
    public void sendAccountCreatedEmail(String toEmail, String username) throws Exception {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setTo(toEmail);
        helper.setSubject("Welcome to AutoRent – Your Account is Ready!");


        String htmlContent = generateAccountCreationHtmlContent(username);

        helper.setText(htmlContent, true);

        helper.setFrom("support@autorent.com");
        mailSender.send(mimeMessage);
    }
    @Override
    public void sendBookingResponseEmail(String toEmail, String username, String status) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setTo(toEmail);
        helper.setSubject("Booking Status - AutoRent");

        String htmlContent = generateBookingResponseHtmlContent(username, status);

        helper.setText(htmlContent, true); // true enables HTML content
        helper.setFrom("support@autorent.com");

        mailSender.send(mimeMessage);
    }
    public String generateBookingResponseHtmlContent(String username, String status) {
        String htmlTemplate = """
        <!DOCTYPE html>
        <html lang="en">
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <style>
                body {
                    font-family: Arial, sans-serif;
                    margin: 0;
                    padding: 0;
                    background-color: #f4f4f4;
                }
                .email-container {
                    max-width: 600px;
                    margin: 20px auto;
                    background-color: #ffffff;
                    border-radius: 10px;
                    overflow: hidden;
                    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
                }
                .header {
                    background-color: #4CAF50;
                    color: white;
                    padding: 20px;
                    text-align: center;
                }
                .header h1 {
                    margin: 0;
                    font-size: 24px;
                }
                .content {
                    padding: 20px;
                }
                .content p {
                    margin: 10px 0;
                    line-height: 1.6;
                    color: #333;
                }
                .cta-button {
                    display: inline-block;
                    background-color: #4CAF50;
                    color: white;
                    text-decoration: none;
                    padding: 10px 20px;
                    border-radius: 5px;
                    margin-top: 20px;
                }
                .cta-button:hover {
                    background-color: #45a049;
                }
                .footer {
                    background-color: #f4f4f4;
                    text-align: center;
                    padding: 10px;
                    font-size: 12px;
                    color: #777;
                }
            </style>
        </head>
        <body>
            <div class="email-container">
                <div class="header">
                    <h1>AutoRent - Booking Status</h1>
                </div>
                <div class="content">
                    <p>Hi %s,</p>
                    <p>We wanted to inform you about the status of your recent booking with AutoRent.</p>
                    %s
                    <p>If you have any questions or need further assistance, feel free to contact our support team.</p>
                    <p>Best regards,</p>
                    <p>The AutoRent Support Team</p>
                </div>
                <div class="footer">
                    <p>You're receiving this email because you recently made a booking with AutoRent. If you didn't make this booking, please disregard this message.</p>
                </div>
            </div>
        </body>
        </html>
        """;

        String statusMessage = "";

        if ("Approved".equalsIgnoreCase(status)) {
            statusMessage = "<p style='color: green;'>Your booking has been approved! Your vehicle will be ready for pick-up at the scheduled time. We look forward to serving you.</p>";
        } else if ("Rejected".equalsIgnoreCase(status)) {
            statusMessage = "<p style='color: red;'>Unfortunately, your booking has been rejected. Please feel free to reach out to us if you need assistance or have any questions regarding this decision.</p>";
        } else {
            statusMessage = "<p>There was an issue processing your booking. Please contact our support team for further assistance.</p>";
        }

        return String.format(htmlTemplate, username, statusMessage);
    }

    public String generateAccountCreationHtmlContent(String username) {
        String htmlTemplate = """
        <!DOCTYPE html>
        <html lang="en">
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <style>
                body {
                    font-family: Arial, sans-serif;
                    margin: 0;
                    padding: 0;
                    background-color: #f4f4f4;
                }
                .email-container {
                    max-width: 600px;
                    margin: 20px auto;
                    background-color: #ffffff;
                    border-radius: 10px;
                    overflow: hidden;
                    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
                }
                .header {
                    background-color: #004aad;
                    color: white;
                    padding: 20px;
                    text-align: center;
                }
                .header h1 {
                    margin: 0;
                    font-size: 24px;
                }
                .content {
                    padding: 20px;
                }
                .content p {
                    margin: 10px 0;
                    line-height: 1.6;
                    color: #333;
                }
                .cta-button {
                    display: inline-block;
                    background-color: #28a745;
                    color: white;
                    text-decoration: none;
                    padding: 10px 20px;
                    border-radius: 5px;
                    margin-top: 20px;
                    text-align: center;
                }
                .cta-button:hover {
                    background-color: #218838;
                }
                .footer {
                    background-color: #f4f4f4;
                    text-align: center;
                    padding: 10px;
                    font-size: 12px;
                    color: #777;
                }
                .footer a {
                    color: #004aad;
                    text-decoration: none;
                }
            </style>
        </head>
        <body>
            <div class="email-container">
                <div class="header">
                    <h1>Welcome to AutoRent!</h1>
                </div>
                <div class="content">
                    <p>Hi %s,</p>
                    <p>Thank you for creating an account with AutoRent! We’re excited to help you with all your vehicle rental needs.</p>
                    <p>With AutoRent, you have access to a wide range of vehicles, flexible rental options, and reliable customer support. You're just a few steps away from enjoying all the benefits of our platform.</p>
                    <p style="text-align: center;">
                        <a href="http://localhost:4200/login" class="cta-button">Log in to Your Account</a>
                    </p>
                    <p>If you need any assistance, feel free to reach out to our support team at <a href="mailto:support@autorent.com">support@autorent.com</a>.</p>
                    <p>We look forward to providing you with an exceptional experience!</p>
                    <p>Best regards,</p>
                    <p>The AutoRent Team</p>
                </div>
                <div class="footer">
                    <p>You're receiving this email because you signed up for AutoRent. If you didn't, please ignore this email.</p>
                    <p><a href="https://autorent.com/unsubscribe">Unsubscribe</a></p>
                </div>
            </div>
        </body>
        </html>
        """;
        return String.format(htmlTemplate, username);
    }
}
