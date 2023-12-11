package com.example.tasklist.model.user;

import com.example.tasklist.model.task.Task;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class User {

  @Column(name = "user_id")
  private long userId;
  private String name;
  private String username;
  private String password;
  @Column(name = "password_confirmation")
  private String passwordConfirmation;
  private Set<Role> roles;
  private List<Task> tasks;
}
