package com.example.tasklist.web.controller;

import com.example.tasklist.domain.task.Task;
import com.example.tasklist.services.TaskService;
import com.example.tasklist.web.dto.task.TaskDto;
import com.example.tasklist.web.dto.validation.OnCreate;
import com.example.tasklist.web.dto.validation.OnUpdate;
import com.example.tasklist.web.mappers.TaskMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tasks")
public class TaskController {

  private final TaskService taskService;
  private final TaskMapper taskMapper;

  @PutMapping
  public TaskDto update(@Validated(OnUpdate.class) @RequestBody TaskDto taskDto) {
    Task task = taskMapper.dtoToEntity(taskDto);
    Task updatedTask = taskService.update(task);
    return taskMapper.entityToDto(updatedTask);
  }

  @GetMapping("/{id}")
  public TaskDto getById(@PathVariable("id") long taskId) {
    Task task = taskService.getById(taskId);
    return taskMapper.entityToDto(task);
  }

  @DeleteMapping("/{id}")
  public void deleteById(@PathVariable("id") long taskId) {
    taskService.delete(taskId);
  }

  @GetMapping("/{id}/tasks")
  public List<TaskDto> getAllByUserId(@PathVariable("id") long userId) {
    List<Task> entities = taskService.getAllByUserId(userId);
    return taskMapper.entityToDto(entities);
  }

  @PostMapping("/{id}/tasks")
  public TaskDto createTask(
          @PathVariable("id") long userId,
          @Validated(OnCreate.class) @RequestBody TaskDto taskDto
  ) {

    Task taskEntity = taskMapper.dtoToEntity(taskDto);
    Task createdTask = taskService.create(taskEntity, userId);

    return taskMapper.entityToDto(createdTask);
  }
}
