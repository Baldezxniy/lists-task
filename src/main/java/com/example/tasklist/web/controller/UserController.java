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
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
  private final UserService userService;
  private final UserMapper userMapper;

  private final TaskService taskService;
  private final TaskMapper taskMapper;

  @PutMapping
  public UserDto update(@Validated(OnUpdate.class) @RequestBody UserDto userDto) {
    User user = userMapper.dtoToEntity(userDto);
    User updatedUser = userService.update(user);
    return userMapper.entityToDto(updatedUser);
  }

  @GetMapping("/{id}")
  public UserDto getById(@PathVariable("id") long userId) {
    User user = userService.getById(userId);
    return userMapper.entityToDto(user);
  }

  @DeleteMapping("/{id}")
  public void deleteById(@PathVariable("id") long userId) {
    userService.delete(userId);
  }

  @GetMapping("/{id}/tasks")
  public List<TaskDto> getTasksByUserId(@PathVariable("id") final Long userId) {
    List<Task> tasks = taskService.getAllByUserId(userId);
    return taskMapper.entityToDto(tasks);
  }

  @PostMapping("/{id}/tasks")
  public TaskDto createTask(@PathVariable Long id,
                            @Validated(OnCreate.class) @RequestBody TaskDto dto) {
    Task task = taskMapper.dtoToEntity(dto);
    Task createdTask = taskService.create(task, id);
    return taskMapper.entityToDto(createdTask);
  }
}
