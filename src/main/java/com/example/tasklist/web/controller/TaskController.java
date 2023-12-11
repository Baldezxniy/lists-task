package com.example.tasklist.web.controller;

import com.example.tasklist.model.task.Task;
import com.example.tasklist.services.TaskService;
import com.example.tasklist.web.dto.task.TaskDto;
import com.example.tasklist.web.dto.validation.OnCreate;
import com.example.tasklist.web.dto.validation.OnUpdate;
import com.example.tasklist.web.mappers.TaskMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/task")
@RequiredArgsConstructor
@Validated
public class TaskController {

  private final TaskService taskService;
  private final TaskMapper taskMapper;

  @PutMapping
  public TaskDto update(@Validated(OnUpdate.class) @RequestBody TaskDto taskDto) {
    Task task = taskMapper.dtoToEntity(taskDto);
    Task updatedTask = taskService.update(task);
    return taskMapper.etityToDto(updatedTask);

  }

  @GetMapping("/{id}")
  public TaskDto getById(@PathVariable("id") long taskId) {
    Task task = taskService.getById(taskId);
    return taskMapper.etityToDto(task);
  }

  @DeleteMapping("/{id}")
  public void deleteById(@PathVariable("id") long taskId) {
    taskService.delete(taskId);
  }

  @GetMapping("/{id}/tasks")
  public List<TaskDto> getAllByUserId(@PathVariable("id") long userId) {
    List<Task> entities = taskService.getAllByUserId(userId);
    return taskMapper.etityToDto(entities);
  }

  @PostMapping("/{id}/tasks")
  public TaskDto createTask(
          @PathVariable("id") long userId,
          @Validated(OnCreate.class) @RequestBody TaskDto taskDto
  ) {

    Task taskEntity = taskMapper.dtoToEntity(taskDto);
    Task createdTask = taskService.create(taskEntity, userId);

    return taskMapper.etityToDto(createdTask);
  }
}
