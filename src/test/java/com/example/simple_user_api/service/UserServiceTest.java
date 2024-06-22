package com.example.simple_user_api.service;

import com.example.simple_user_api.model.User;
import com.example.simple_user_api.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
  @Mock
  private UserRepository userRepository;

  @Mock
  private EmailService emailService;

  @InjectMocks
  private UserService userService;

  @Test
  @DisplayName("Test getting a user by ID - User Exists")
  void testGetUserById_UserExists() {
    var id = 123L;
    var user = new User();
    user.setId(id);

    when(userRepository.findById(id)).thenReturn(Optional.of(user));

    var searchedUserId = userService.getUserById(id);
    assertNotNull(searchedUserId);
    assertEquals(id, searchedUserId.getId());
  }

  @Test
  @DisplayName("Test get user by ID - User Does Not Exists")
  void testGetUserById_UserDoesNotExist() {
    var id = 123L;
    when(userRepository.findById(id)).thenReturn(Optional.empty());
    User foundUser = userService.getUserById(id);
    assertNull(foundUser);
  }

  @Test
  @DisplayName("Test getting all users")
  void testGetUsers() {
    List<User> users = Arrays.asList(new User(), new User());
    when(userRepository.findAll()).thenReturn(users);

    List<User> foundUsers = userService.getUsers();
    assertEquals(2, foundUsers.size());
  }

  @Test
  @DisplayName("Test adding a user")
  void addUser() throws Exception {
    User user = new User();
    String email = "test@example.com";
    String title = "Registration Successful";
    user.setEmail(email);
    doNothing().when(emailService).sendSimpleEmail(anyString(), anyString(), anyString());

    userService.addUser(user);

    verify(userRepository, times(1)).save(user);
    verify(emailService, times(1)).sendSimpleEmail(eq(email), eq(title), anyString());
  }

  @Test
  @DisplayName("Test deleting a user")
  void deleteUser() {
    var userId = 1L;
    var user = new User();
    user.setId(userId);
    when(userRepository.findById(userId)).thenReturn(Optional.of(user));

    userService.deleteUser(userId);
    verify(userRepository, times(1)).delete(user);
  }

  @Test
  @DisplayName("Test updating a user")
  void updateUser() {
    var userId = 1L;
    var user = new User();
    user.setFirstName("NewFirstName");
    user.setLastName("NewLastName");
    user.setEmail("new@example.com");

    var existingUser = new User();
    existingUser.setId(userId);
    existingUser.setFirstName("OldFirstName");
    existingUser.setLastName("OldLastName");
    existingUser.setEmail("old@example.com");

    when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

    userService.updateUser(userId, user);

    assertEquals("NewFirstName", existingUser.getFirstName());
    assertEquals("NewLastName", existingUser.getLastName());
    assertEquals("new@example.com", existingUser.getEmail());
    verify(userRepository, times(1)).save(existingUser);
  }
}
