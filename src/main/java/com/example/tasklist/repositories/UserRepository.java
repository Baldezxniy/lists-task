package com.example.tasklist.repositories;

import com.example.tasklist.domain.user.Role;
import com.example.tasklist.domain.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface UserRepository {
  Optional<User> findById(long userId);

  Optional<User> findByUsername(String username);

  void update(User user);

  void create(User user);

  void insertUserRole(@Param("userId") long userId, @Param("role") Role role);

  boolean isTaskOwner(@Param("userId") long userId, @Param("taskId") long taskId);

  void delete(long userId);
}
