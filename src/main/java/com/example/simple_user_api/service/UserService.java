package com.example.simple_user_api.service;

import com.example.simple_user_api.model.User;
import com.example.simple_user_api.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User getUserById(long id) {
    return userRepository.findById(id).orElse(null);
  }

  public List<User> getUsers() {
    return userRepository.findAll();
  }

  public User addUser(User user) {
    return userRepository.save(user);
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
