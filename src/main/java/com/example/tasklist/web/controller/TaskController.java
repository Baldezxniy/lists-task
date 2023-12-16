package com.example.tasklist.web.controller;

import com.example.tasklist.domain.task.Task;
import com.example.tasklist.services.TaskService;
import com.example.tasklist.web.dto.task.TaskDto;
import com.example.tasklist.web.dto.validation.OnCreate;
import com.example.tasklist.web.dto.validation.OnUpdate;
import com.example.tasklist.web.mappers.TaskMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tasks")
@Tag(name = "Task Controller", description = "Task API")
public class TaskController {

  private final TaskService taskService;
  private final TaskMapper taskMapper;

  @PutMapping
  @Operation(summary = "Update task.")
  public TaskDto update(@Validated(OnUpdate.class) @RequestBody TaskDto taskDto) {
    Task task = taskMapper.dtoToEntity(taskDto);
    Task updatedTask = taskService.update(task);
    return taskMapper.entityToDto(updatedTask);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Get task by task id.")
  public TaskDto getById(@PathVariable("id") long taskId) {
    Task task = taskService.getById(taskId);
    return taskMapper.entityToDto(task);
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete task by task id.")
  public void deleteById(@PathVariable("id") long taskId) {
    taskService.delete(taskId);
  }
}
