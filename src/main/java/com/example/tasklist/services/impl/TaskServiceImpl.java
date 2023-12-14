package com.example.tasklist.services.impl;

import com.example.tasklist.model.exceprion.NotFoundException;
import com.example.tasklist.model.task.Status;
import com.example.tasklist.model.task.Task;
import com.example.tasklist.repositories.TaskRepository;
import com.example.tasklist.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

  private final TaskRepository taskRepository;

  @Override
  @Transactional(readOnly = true)
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
  public Task update(Task task) {
    if (task.getStatus() == null) {
      task.setStatus(Status.TODO);
    }

    taskRepository.update(task);
    return task;
  }

  @Override
  @Transactional
  public Task create(Task task, long userId) {
    task.setStatus(Status.TODO);
    taskRepository.create(task);
    taskRepository.assignToUserById(task.getTaskId(), userId);
    return task;
  }

  @Override
  @Transactional
  public void delete(long taskId) {
    taskRepository.delete(taskId);
  }
}
