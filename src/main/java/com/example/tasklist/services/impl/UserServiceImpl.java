package com.example.tasklist.services.impl;

import com.example.tasklist.model.user.User;
import com.example.tasklist.services.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
  @Override
  public User getById(long userId) {
    return null;
  }

  @Override
  public User getByUsername(String username) {
    return null;
  }

  @Override
  public User update(User user) {
    return null;
  }

  @Override
  public User create(User user) {
    return null;
  }

  @Override
  public boolean isTaskOwner(long userId, long taskId) {
    return false;
  }

  @Override
  public void delete(long userId) {

  }
}
