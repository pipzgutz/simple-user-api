package com.example.simple_user_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "simple_user")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @NotBlank(message = "First name is mandatory")
  @Size(min = 2, max = 30)
  @Column(name = "first_name", nullable = false)
  private String firstName;

  @NotBlank(message = "Last name is mandatory")
  @Size(min = 2, max = 30)
  @Column(name = "last_name", nullable = false)
  private String lastName;

  @NotBlank(message = "Email is mandatory")
  @Email(message = "Email should be valid")
  @Column(name = "email", nullable = false, unique = true)
  private String email;
}
