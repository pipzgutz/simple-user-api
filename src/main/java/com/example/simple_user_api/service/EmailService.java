package com.example.simple_user_api.service;

import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
  @Value("${simple-user.from-email}")
  private String fromEmail;
  private final JavaMailSender mailSender;

  public EmailService(JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  public void sendSimpleEmail(String to, String subject, String body) throws MessagingException {
    var message = mailSender.createMimeMessage();
    var messageHelper = new MimeMessageHelper(message, true);
    messageHelper.setFrom(fromEmail);
    messageHelper.setText(body, true);
    messageHelper.setTo(to);
    message.setSubject(subject);
    mailSender.send(message);
  }
}
