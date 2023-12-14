package com.example.tasklist.repositories;

import com.example.tasklist.model.task.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {
  Optional<Task> findById(long taskId);

  List<Task> findAllByUserId(long userId);

  void assignToUserById(long taskId, long userId);

  void update(Task task);

  void create(Task task);

  void delete(long taskId);
}