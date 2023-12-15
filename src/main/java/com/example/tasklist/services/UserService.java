package com.example.tasklist.services;

import com.example.tasklist.domain.user.User;

public interface UserService {
  User getById(long userId);

  User getByUsername(String username);

  User update(User user);

  User create(User user);

  boolean isTaskOwner(long userId, long taskId);

  void delete(long userId);
}
