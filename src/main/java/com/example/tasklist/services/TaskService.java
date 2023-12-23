package com.example.tasklist.services;

import com.example.tasklist.domain.task.Task;
import com.example.tasklist.domain.task.TaskImage;

import java.time.Duration;
import java.util.List;

public interface TaskService {
  Task getById(long taskId);

  List<Task> getAllByUserId(long userId);

  List<Task> getAllSoonTasks(Duration duration);

  Task update(Task task);

  Task create(Task task, long id);

  void delete(long taskId);

  void uploadImage(long taskId, TaskImage image);
}
