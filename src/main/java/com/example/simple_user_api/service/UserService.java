package com.example.simple_user_api.service;

import com.example.simple_user_api.model.User;
import com.example.simple_user_api.repository.UserRepository;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
  private final UserRepository userRepository;
  private final EmailService emailService;

  public UserService(UserRepository userRepository, EmailService emailService) {
    this.userRepository = userRepository;
    this.emailService = emailService;
  }

  public User getUserById(long id) {
    return userRepository.findById(id).orElse(null);
  }

  public List<User> getUsers() {
    return userRepository.findAll();
  }

  public void addUser(User user) {
    userRepository.save(user);
    String subject = "Registration Successful";
    String htmlContent = """
      <h1>Simple Email</h1>
      <p>Hello from ABC Company!</p>
      """;
    try {
      emailService.sendSimpleEmail(user.getEmail(), subject, htmlContent);
    } catch (MessagingException e) {
      throw new RuntimeException(e);
    }
  }

  public void deleteUser(long id) {
    var userOpt = userRepository.findById(id);
    userOpt.ifPresent(userRepository::delete);
  }

  public void updateUser(long id, User user) {
    var userOpt = userRepository.findById(id);
    if (userOpt.isPresent()) {
      var userTemp = userOpt.get();
      userTemp.setFirstName(user.getFirstName());
      userTemp.setLastName(user.getLastName());
      userTemp.setEmail(user.getEmail());
      userRepository.save(userTemp);
    }
  }
}
