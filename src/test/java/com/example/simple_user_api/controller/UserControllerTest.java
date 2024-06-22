package com.example.simple_user_api.controller;

import com.example.simple_user_api.model.User;
import com.example.simple_user_api.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserService userService;

  @Autowired
  private ObjectMapper objectMapper;

  private User user;

  @BeforeEach
  void setUp() {
    user = new User();
    user.setId(1L);
    user.setFirstName("John");
    user.setLastName("Doe");
    user.setEmail("john.doe@example.com");
  }

  @Test
  void testGetUsers() throws Exception {
    var users = Collections.singletonList(user);
    when(userService.getUsers()).thenReturn(users);

    mockMvc.perform(get("/api/user"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.size()").value(users.size()))
      .andExpect(jsonPath("$[0].firstName").value(user.getFirstName()));
  }

  @Test
  void testGetUser() throws Exception {
    when(userService.getUserById(anyLong())).thenReturn(Optional.of(user));

    mockMvc.perform(get("/api/user/{id}", user.getId()))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.firstName").value(user.getFirstName()));
  }

  @Test
  void testGetUser_UserNotFound() throws Exception {
    var userId = 1L;
    when(userService.getUserById(userId)).thenReturn(Optional.empty());

    mockMvc.perform(get("/api/user/{id}", userId))
      .andExpect(status().isNotFound())
      .andExpect(jsonPath("$.message").value("User not found with id: 1"));
  }

  @Test
  void testAddUser() throws Exception {
    doNothing().when(userService).addUser(any(User.class));

    mockMvc.perform(post("/api/user")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(user)))
      .andExpect(status().isOk());

    verify(userService, times(1)).addUser(any(User.class));
  }

  @Test
  void testDeleteUser() throws Exception {
    doNothing().when(userService).deleteUser(anyLong());

    mockMvc.perform(delete("/api/user/{id}", user.getId()))
      .andExpect(status().isOk());

    verify(userService, times(1)).deleteUser(anyLong());
  }

  @Test
  void testUpdateUser() throws Exception {
    doNothing().when(userService).updateUser(anyLong(), any(User.class));

    mockMvc.perform(put("/api/user/{id}", user.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(user)))
      .andExpect(status().isOk());

    verify(userService, times(1)).updateUser(anyLong(), any(User.class));
  }
}
