package com.example.tasklist.services.impl;

import com.example.tasklist.config.TestConfig;
import com.example.tasklist.domain.MailType;
import com.example.tasklist.domain.exceprion.NotFoundException;
import com.example.tasklist.domain.user.Role;
import com.example.tasklist.domain.user.User;
import com.example.tasklist.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.Properties;
import java.util.Set;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@Import(TestConfig.class)
@ExtendWith(MockitoExtension.class)
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class UserServiceImplTest {

  @MockBean
  private UserRepository userRepository;

  @MockBean
  private PasswordEncoder passwordEncoder;

  @MockBean
  private MailServiceImpl mailService;

  @Autowired
  private UserServiceImpl userService;

  @Test
  void getById() {
    long userId = 1;
    User user = new User();
    user.setUserId(userId);

    Mockito.when(userRepository.findById(userId))
            .thenReturn(Optional.of(user));

    User userTest = userService.getById(userId);
    Mockito.verify(userRepository).findById(userId);
    Assertions.assertEquals(user, userTest);
  }

  @Test
  void getByIdWithNotExistingId() {
    long userId = 1;

    Mockito.when(userRepository.findById(userId))
            .thenReturn(Optional.empty());

    Assertions.assertThrows(NotFoundException.class,
            () -> userService.getById(userId));

    Mockito.verify(userRepository).findById(userId);
  }

  @Test
  void getUserById() {
    String username = "username@user.name";
    User user = new User();
    user.setUsername(username);
    Mockito.when(userRepository.findByUsername(username))
            .thenReturn(Optional.of(user));

    User userTest = userService.getByUsername(username);

    Mockito.verify(userRepository).findByUsername(username);
    Assertions.assertEquals(user, userTest);
  }

  @Test
  void getByUsernameWithNotExistingId() {
    String username = "username@user.name";

    Mockito.when(userRepository.findByUsername(username))
            .thenReturn(Optional.empty());

    Assertions.assertThrows(NotFoundException.class,
            () -> userService.getByUsername(username));

    Mockito.verify(userRepository).findByUsername(username);
  }

  @Test
  void update() {
    long userId = 1L;
    String password = "password";
    User user = new User();
    user.setUserId(userId);
    user.setPassword(password);

    Mockito.when(passwordEncoder.encode(password))
            .thenReturn("encodedPassword");

    Mockito.when(userRepository.findById(user.getUserId()))
            .thenReturn(Optional.of(user));

    User updated = userService.update(user);

    Mockito.verify(passwordEncoder).encode(password);
    Mockito.verify(userRepository).save(user);

    Assertions.assertEquals(user.getUsername(), updated.getUsername());
    Assertions.assertEquals(user.getName(), updated.getName());
  }

  @Test
  void isTaskOwner() {
    long userId = 1L;
    long taskId = 1L;

    Mockito.when(userRepository.isTaskOwner(userId, taskId))
            .thenReturn(true);

    boolean isOwner = userService.isTaskOwner(userId, taskId);

    Mockito.verify(userRepository).isTaskOwner(userId, taskId);

    Assertions.assertTrue(isOwner);
  }

  @Test
  void create() {
    String username = "username@gmail.com";
    String password = "password";

    User user = new User();
    user.setUsername(username);
    user.setPassword(password);
    user.setPasswordConfirmation(password);

    Mockito.when(userRepository.findByUsername(username))
            .thenReturn(Optional.empty());

    Mockito.when(passwordEncoder.encode(password))
            .thenReturn("encodedPassword");

    User testUser = userService.create(user);

    Mockito.verify(userRepository).save(user);
    Mockito.verify(mailService).sendEmail(user, MailType.REGISTRATION, new Properties());

    Assertions.assertEquals(Set.of(Role.ROLE_USER), testUser.getRoles());
    Assertions.assertEquals("encodedPassword", testUser.getPassword());
  }

  @Test
  void createWithExistingUsername() {
    String username = "username@user.name";
    String password = "password";

    User user = new User();
    user.setUsername(username);
    user.setPassword(password);
    user.setPasswordConfirmation(password);

    Mockito.when(userRepository.findByUsername(username))
            .thenReturn(Optional.of(new User()));

    Assertions.assertThrows(IllegalStateException.class,
            () -> userService.create(user));

    Mockito.verify(userRepository, Mockito.never()).save(user);
  }


  @Test
  void createWithDifferentPassword() {
    String username = "username@user.name";
    String password = "password";
    String passwordConfirmation = "password-confirmation";

    User user = new User();
    user.setUsername(username);
    user.setPassword(password);
    user.setPasswordConfirmation(passwordConfirmation);

    Mockito.when(userRepository.findByUsername(username))
            .thenReturn(Optional.empty());

    Assertions.assertThrows(IllegalStateException.class,
            () -> userService.create(user));

    Mockito.verify(userRepository, Mockito.never()).save(user);
  }


  @Test
  void isTaskOwnerWithFalse() {
    long userId = 1L;
    long taskId = 1L;
    Mockito.when(userRepository.isTaskOwner(userId, taskId))
            .thenReturn(false);
    boolean isOwner = userService.isTaskOwner(userId, taskId);
    Mockito.verify(userRepository).isTaskOwner(userId, taskId);
    Assertions.assertFalse(isOwner);
  }

  @Test
  void getTaskAuthor() {
    long taskId = 1L;
    long userId = 1L;
    User user = new User();
    user.setUserId(userId);
    Mockito.when(userRepository.findTaskAuthor(taskId))
            .thenReturn(Optional.of(user));
    User author = userService.getTaskAuthor(taskId);
    Mockito.verify(userRepository).findTaskAuthor(taskId);
    Assertions.assertEquals(user, author);
  }

  @Test
  void getNotExistingTaskAuthor() {
    long taskId = 1L;
    Mockito.when(userRepository.findTaskAuthor(taskId))
            .thenReturn(Optional.empty());
    Assertions.assertThrows(NotFoundException.class, () ->
            userService.getTaskAuthor(taskId));
    Mockito.verify(userRepository).findTaskAuthor(taskId);
  }

  @Test
  void delete() {
    long userId = 1L;
    userService.delete(userId);
    Mockito.verify(userRepository).deleteById(userId);
  }
}
