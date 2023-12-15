package com.example.tasklist.repositories;

import com.example.tasklist.domain.user.Role;
import com.example.tasklist.domain.user.User;

import java.util.Optional;

public interface UserRepository {
  Optional<User> findById(long userId);

  Optional<User> findByUsername(String username);

  void update(User user);

  void create(User user);

  void insertUserRole(long userId, Role role);

  boolean isTaskOwner(long userId, long taskId);

  void delete(long userId);
}
