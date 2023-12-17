package com.example.tasklist.services.impl;

import com.example.tasklist.domain.exceprion.NotFoundException;
import com.example.tasklist.domain.task.Status;
import com.example.tasklist.domain.task.Task;
import com.example.tasklist.domain.user.User;
import com.example.tasklist.repositories.TaskRepository;
import com.example.tasklist.services.TaskService;
import com.example.tasklist.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

  private final TaskRepository taskRepository;
  private final UserService userService;

  @Override
  @Transactional(readOnly = true)
  @Cacheable(value = "TaskService::getById", key = "#taskId")
  public Task getById(long taskId) {
    return taskRepository.findById(taskId).orElseThrow(() -> new NotFoundException("Task not found."));
  }

  @Override
  @Transactional(readOnly = true)
  public List<Task> getAllByUserId(long userId) {
    return taskRepository.findAllByUserId(userId);
  }

  @Override
  @Transactional
  @CachePut(value = "TaskService::getById", key = "#task.taskId")
  public Task update(Task task) {
    if (task.getStatus() == null) {
      task.setStatus(Status.TODO);
    }

    taskRepository.save(task);
    return task;
  }

  @Override
  @Transactional
  @Cacheable(value = "TaskService::getById", key = "#task.taskId")
  public Task create(Task task, long userId) {
    User user = userService.getById(userId);
    task.setStatus(Status.TODO);
    user.getTasks().add(task);

    userService.update(user);
    return task;
  }

  @Override
  @Transactional
  @CacheEvict(value = "TaskService::getById", key = "#taskId")
  public void delete(long taskId) {
    taskRepository.deleteById(taskId);
  }
}
