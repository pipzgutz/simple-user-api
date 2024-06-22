package com.example.simple_user_api.controller;

import com.example.simple_user_api.model.User;
import com.example.simple_user_api.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  public List<User> getUsers() {
    return userService.getUsers();
  }

  @GetMapping("{id}")
  public User getUser(@PathVariable long id) {
    return userService.getUserById(id);
  }

  @PostMapping
  public void deleteUser(@RequestBody User user) {
    userService.addUser(user);
  }

  @DeleteMapping("{id}")
  public void deleteUser(@PathVariable long id) {
    userService.deleteUser(id);
  }

  @PutMapping("{id}")
  public void updateUser(@PathVariable long id, @RequestBody User user) {
    userService.updateUser(id, user);
  }
}
