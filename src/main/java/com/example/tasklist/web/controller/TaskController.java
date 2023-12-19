package com.example.tasklist.web.controller;

import com.example.tasklist.domain.task.Task;
import com.example.tasklist.domain.task.TaskImage;
import com.example.tasklist.services.TaskService;
import com.example.tasklist.web.dto.task.TaskDto;
import com.example.tasklist.web.dto.task.TaskImageDto;
import com.example.tasklist.web.dto.validation.OnUpdate;
import com.example.tasklist.web.mappers.TaskImageMapper;
import com.example.tasklist.web.mappers.TaskMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tasks")
@Tag(name = "Task Controller", description = "Task API")
public class TaskController {

  private final TaskService taskService;
  private final TaskMapper taskMapper;
  private final TaskImageMapper taskImageMapper;

  @PutMapping
  @Operation(summary = "Update task.")
  @PreAuthorize("@customSecurityExpression.canAccessTask(#taskDto.taskId)")
  public TaskDto update(@Validated(OnUpdate.class) @RequestBody TaskDto taskDto) {
    Task task = taskMapper.dtoToEntity(taskDto);
    Task updatedTask = taskService.update(task);
    return taskMapper.entityToDto(updatedTask);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Get task by task id.")
  @PreAuthorize("@customSecurityExpression.canAccessTask(#taskId)")
  public TaskDto getById(@PathVariable("id") long taskId) {
    Task task = taskService.getById(taskId);
    return taskMapper.entityToDto(task);
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete task by task id.")
  @PreAuthorize("@customSecurityExpression.canAccessTask(#taskId)")
  public void deleteById(@PathVariable("id") long taskId) {
    taskService.delete(taskId);
  }

  @PostMapping("/{id}/image")
  @Operation(summary = "Upload image to task")
  @PreAuthorize("@customSecurityExpression.canAccessTask(#taskId)")
  public void uploadImage(@PathVariable("id") long taskId, @Validated @ModelAttribute TaskImageDto imageDto) {
    TaskImage image = taskImageMapper.dtoToEntity(imageDto);
    taskService.uploadImage(taskId, image);
  }
}
