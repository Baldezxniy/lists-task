package com.example.tasklist.services.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import lombok.RequiredArgsConstructor;

import com.example.tasklist.domain.exceprion.NotFoundException;
import com.example.tasklist.domain.user.User;
import com.example.tasklist.repositories.UserRepository;
import com.example.tasklist.services.UserService;
import com.example.tasklist.domain.user.Role;

import static com.example.tasklist.domain.user.Role.ROLE_USER;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  @Transactional(readOnly = true)
  public User getById(long userId) {
    return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found."));
  }

  @Override
  @Transactional(readOnly = true)
  public User getByUsername(String username) {
    System.out.println("GET USER " + username);
    return userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("User not found."));
  }

  @Override
  @Transactional
  public User update(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    userRepository.update(user);
    return user;
  }

  @Override
  @Transactional
  public User create(User user) {
    if (userRepository.findByUsername(user.getUsername()).isPresent()) {
      throw new IllegalStateException("User already exists.");
    }

    if (!user.getPassword().equals(user.getPasswordConfirmation())) {
      throw new IllegalStateException("Password and password confirmation do not match");
    }

    user.setPassword(passwordEncoder.encode(user.getPassword()));
    userRepository.create(user);
    Set<Role> roles = Set.of(ROLE_USER);
    userRepository.insertUserRole(user.getUserId(), ROLE_USER);
    user.setRoles(roles);
    return user;
  }

  @Override
  @Transactional(readOnly = true)
  public boolean isTaskOwner(long userId, long taskId) {
    return userRepository.isTaskOwner(userId, taskId);
  }

  @Override
  @Transactional
  public void delete(long userId) {
    userRepository.delete(userId);
  }
}
