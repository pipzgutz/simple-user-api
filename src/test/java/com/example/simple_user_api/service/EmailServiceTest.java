package com.example.simple_user_api.service;

import jakarta.mail.Address;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {
  @Mock
  private JavaMailSender mailSender;

  @InjectMocks
  private EmailService emailService;

  @Mock
  private MimeMessage mimeMessage;

  @BeforeEach
  void setUp() {
    when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
  }

  @Test
  @DisplayName("Send simple email")
  public void testSendSimpleEmail() throws Exception {
    ReflectionTestUtils.setField(emailService, "fromEmail", "hello@example.com");

    var to = "test@example.com";
    var subject = "Test Subject";
    var htmlBody = "<h1>Test Message</h1>";

    doNothing().when(mailSender).send(any(MimeMessage.class));
    when(mimeMessage.getRecipients(MimeMessage.RecipientType.TO)).thenReturn(new Address[]{new InternetAddress(to)});
    when(mimeMessage.getSubject()).thenReturn(subject);
    when(mimeMessage.getContent()).thenReturn(htmlBody);

    emailService.sendSimpleEmail(to, subject, htmlBody);

    verify(mailSender, times(1)).createMimeMessage();
    verify(mailSender, times(1)).send(mimeMessage);
    var messageCaptor = ArgumentCaptor.forClass(MimeMessage.class);
    verify(mailSender).send(messageCaptor.capture());
    var capturedMessage = messageCaptor.getValue();

    assertEquals(to, ((InternetAddress) capturedMessage.getRecipients(MimeMessage.RecipientType.TO)[0]).getAddress());
    assertEquals(subject, capturedMessage.getSubject());
    assertEquals(htmlBody, capturedMessage.getContent().toString().trim());
  }
}
