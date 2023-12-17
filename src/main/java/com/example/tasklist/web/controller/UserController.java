package com.example.tasklist.web.controller;

import com.example.tasklist.domain.task.Task;
import com.example.tasklist.domain.user.User;
import com.example.tasklist.services.TaskService;
import com.example.tasklist.services.UserService;
import com.example.tasklist.web.dto.task.TaskDto;
import com.example.tasklist.web.dto.user.UserDto;
import com.example.tasklist.web.dto.validation.OnCreate;
import com.example.tasklist.web.dto.validation.OnUpdate;
import com.example.tasklist.web.mappers.TaskMapper;
import com.example.tasklist.web.mappers.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Tag(name = "User Controller", description = "User API")
public class UserController {
  private final UserService userService;
  private final UserMapper userMapper;

  private final TaskService taskService;
  private final TaskMapper taskMapper;

  @PutMapping
  @Operation(summary = "Update user.")
  @PreAuthorize("@customSecurityExpression.canAccessUser(#userDto.userId)")
  public UserDto update(@Validated(OnUpdate.class) @RequestBody UserDto userDto) {
    User user = userMapper.dtoToEntity(userDto);
    User updatedUser = userService.update(user);
    return userMapper.entityToDto(updatedUser);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Get userDto by id.")
  @PreAuthorize("@customSecurityExpression.canAccessUser(#userId)")
  public UserDto getById(@PathVariable("id") long userId) {
    User user = userService.getById(userId);
    return userMapper.entityToDto(user);
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete user by id.")
  @PreAuthorize("@customSecurityExpression.canAccessUser(#userId)")
  public void deleteById(@PathVariable("id") long userId) {
    userService.delete(userId);
  }

  @GetMapping("/{id}/tasks")
  @Operation(summary = "Get all user's task by user id.")
  @PreAuthorize("@customSecurityExpression.canAccessUser(#userId)")
  public List<TaskDto> getTasksByUserId(@PathVariable("id") final Long userId) {
    List<Task> tasks = taskService.getAllByUserId(userId);
    return taskMapper.entityToDto(tasks);
  }

  @PostMapping("/{id}/tasks")
  @Operation(summary = "Create task for user by user id.")
  @PreAuthorize("@customSecurityExpression.canAccessUser(#userId)")
  public TaskDto createTask(@PathVariable("id") Long userId, @Validated(OnCreate.class) @RequestBody TaskDto dto) {
    Task task = taskMapper.dtoToEntity(dto);
    Task createdTask = taskService.create(task, userId);
    return taskMapper.entityToDto(createdTask);
  }
}
