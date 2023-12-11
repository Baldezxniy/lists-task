package com.example.tasklist.repositories.impl;

import com.example.tasklist.model.user.Role;
import com.example.tasklist.model.user.User;
import com.example.tasklist.repositories.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {
  @Override
  public Optional<User> findById(long userId) {
    return Optional.empty();
  }

  @Override
  public Optional<User> findByUsername(String username) {
    return Optional.empty();
  }

  @Override
  public void update(User user) {

  }

  @Override
  public void create(User user) {

  }

  @Override
  public void insertUserRole(long userId, Role role) {

  }

  @Override
  public boolean isTaskOwner(long userId, long taskId) {
    return false;
  }

  @Override
  public void delete(long userId) {

  }
}
