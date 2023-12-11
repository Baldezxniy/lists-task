package com.example.tasklist.services.impl;

import com.example.tasklist.model.task.Task;
import com.example.tasklist.services.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
  @Override
  public Task getById(long taskId) {
    return null;
  }

  @Override
  public List<Task> getAllByUserId(long userId) {
    return null;
  }

  @Override
  public Task update(Task task) {
    return null;
  }

  @Override
  public Task create(Task task, long userId) {
    return null;
  }

  @Override
  public void delete(long taskId) {

  }
}
