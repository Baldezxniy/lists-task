package com.example.tasklist.services.impl;

import com.example.tasklist.config.TestConfig;
import com.example.tasklist.domain.exceprion.NotFoundException;
import com.example.tasklist.domain.user.Role;
import com.example.tasklist.domain.user.User;
import com.example.tasklist.repositories.TaskRepository;
import com.example.tasklist.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
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
  private TaskRepository taskRepository;

  @MockBean
  private AuthenticationManager authenticationManager;

  @MockBean
  private PasswordEncoder passwordEncoder;

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
    String password = "password";
    User user = new User();
    user.setPassword(password);

    userService.update(user);

    Mockito.verify(passwordEncoder).encode(password);
    Mockito.verify(userRepository).save(user);
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
    String username = "username@user.name";
    String password = "password";

    User user = new User();
    user.setUsername(username);
    user.setPassword(password);
    user.setPasswordConfirmation(password);

    Mockito.when(userRepository.findByUsername(username))
            .thenReturn(Optional.empty());

    User userTest = userService.create(user);

    Mockito.verify(userRepository).save(user);
    Mockito.verify(passwordEncoder).encode(password);
    Assertions.assertEquals(Set.of(Role.ROLE_USER), userTest.getRoles());
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
  void delete() {
    long userId = 1L;
    userService.delete(userId);
    Mockito.verify(userRepository).deleteById(userId);
  }
}
