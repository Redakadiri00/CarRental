package com.CarRentalProject.CarRental.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailSender {

    @Autowired
    private JavaMailSender mailSender;

    public void sendVerificationEmail(String email, String token, String domain) {
        String verificationLink = domain + "/api/auth/verify?token=" + token;

        // Create the email content
        String subject = "Email Verification";
        String content = "<p>Dear User,</p>"
                + "<p>Thank you for registering. Please click the link below to verify your email address:</p>"
                + "<p><a href=\"" + verificationLink + "\">Verify Email</a></p>"
                + "<p>If you did not register, please ignore this email.</p>";

        // Send the email
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(content, true); // `true` for HTML content

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
