package com.example.tasklist.repositories.impl;

import com.example.tasklist.model.task.Task;
import com.example.tasklist.repositories.TaskRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TaskRepositoryImpl implements TaskRepository {
  @Override
  public Optional<Task> findById(long taskId) {
    return Optional.empty();
  }

  @Override
  public List<Task> findAllByUserId(long userId) {
    return null;
  }

  @Override
  public void assignToUserById(long userId) {

  }

  @Override
  public void update(Task task) {

  }

  @Override
  public void create(Task task) {

  }

  @Override
  public void delete(long taskId) {

  }
}
