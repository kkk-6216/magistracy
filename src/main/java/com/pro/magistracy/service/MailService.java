package com.pro.magistracy.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class MailService {


    private final JavaMailSender emailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    public MailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendEmail(String to, String subject, String textHtml, boolean isHtmlText) {
        MimeMessage message = emailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(isHtmlText ? textHtml : readHtmlFile(textHtml), true);
            emailSender.send(message);
        } catch (MessagingException | IOException ignored) {}
    }

    private String readHtmlFile(String htmlFilePath) throws IOException {
        byte[] encodedHtml = Files.readAllBytes(Paths.get(htmlFilePath));
        return new String(encodedHtml, StandardCharsets.UTF_8);
    }
}
