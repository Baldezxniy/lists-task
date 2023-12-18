package com.example.tasklist.domain.user;

import com.example.tasklist.domain.task.Task;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User implements Serializable {

  @Id
  @Column(name = "user_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long userId;

  private String name;
  private String username;
  private String password;

  @Transient
  private String passwordConfirmation;

  @Column(name = "role")
  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"))
  @Enumerated(value = EnumType.STRING)
  private Set<Role> roles;
  @CollectionTable(name = "users_tasks")
  @OneToMany
  @JoinColumn(name = "task_id")
  private List<Task> tasks;
}
