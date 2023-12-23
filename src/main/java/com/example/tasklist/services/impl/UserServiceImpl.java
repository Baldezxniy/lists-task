package com.example.tasklist.services.impl;

import com.example.tasklist.domain.MailType;
import com.example.tasklist.domain.exceprion.NotFoundException;
import com.example.tasklist.domain.user.Role;
import com.example.tasklist.domain.user.User;
import com.example.tasklist.repositories.UserRepository;
import com.example.tasklist.services.MailService;
import com.example.tasklist.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Properties;
import java.util.Set;

import static com.example.tasklist.domain.user.Role.ROLE_USER;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final MailService mailService;

  @Override
  @Transactional(readOnly = true)
  @Cacheable(value = "UserService::getById", key = "#userId")
  public User getById(final long userId) {
    return userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException("User not found."));
  }

  @Override
  @Transactional(readOnly = true)
  @Cacheable(value = "UserService::getByUsername", key = "#username")
  public User getByUsername(final String username) {
    return userRepository.findByUsername(username)
            .orElseThrow(() -> new NotFoundException("User not found."));
  }

  @Override
  @Transactional
  @Caching(put = {
          @CachePut(value = "UserService::getById", key = "#user.userId"),
          @CachePut(value = "UserService::getByUsername", key = "#user.username")
  })
  public User update(final User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    userRepository.save(user);
    return user;
  }

  @Override
  @Transactional
  @Caching(cacheable = {
          @Cacheable(value = "UserService::getById", condition = "#user.userId!=null", key = "#user.userId"),
          @Cacheable(value = "UserService::getByUsername", condition = "#user.username!=null", key = "#user.username")
  })
  public User create(final User user) {
    if (userRepository.findByUsername(user.getUsername()).isPresent()) {
      throw new IllegalStateException("User already exists.");
    }

    if (!user.getPassword().equals(user.getPasswordConfirmation())) {
      throw new IllegalStateException("Password and password confirmation do not match");
    }

    user.setPassword(passwordEncoder.encode(user.getPassword()));
    Set<Role> roles = Set.of(ROLE_USER);
    user.setRoles(roles);
    userRepository.save(user);

    mailService.sendEmail(user, MailType.REGISTRATION, new Properties());
    return user;
  }

  @Override
  @Transactional(readOnly = true)
  @Cacheable(value = "UserService::isTaskOwner", key = "#userId" + '.' + "#taskId")
  public boolean isTaskOwner(final long userId, final long taskId) {
    return userRepository.isTaskOwner(userId, taskId);
  }

  @Override
  @Transactional(readOnly = true)
  @Cacheable(value = "UserService::getTaskAuthor", key = "#taskId")
  public User getTaskAuthor(final long taskId) {
    return userRepository.findTaskAuthor(taskId)
            .orElseThrow(() -> new NotFoundException("User not found."));
  }

  @Override
  @Transactional
  @CacheEvict(value = "UserService::getById", key = "#userId")
  public void delete(final long userId) {
    userRepository.deleteById(userId);
  }
}
