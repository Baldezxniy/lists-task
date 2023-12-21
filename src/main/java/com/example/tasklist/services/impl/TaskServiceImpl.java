package com.example.tasklist.services.impl;

import com.example.tasklist.domain.exceprion.NotFoundException;
import com.example.tasklist.domain.task.Status;
import com.example.tasklist.domain.task.Task;
import com.example.tasklist.domain.task.TaskImage;
import com.example.tasklist.repositories.TaskRepository;
import com.example.tasklist.services.ImageService;
import com.example.tasklist.services.TaskService;
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
  private final ImageService imageService;

  @Override
  @Transactional(readOnly = true)
  @Cacheable(value = "TaskService::getById", key = "#taskId")
  public Task getById(final long taskId) {
    return taskRepository.findById(taskId).orElseThrow(() -> new NotFoundException("Task not found."));
  }

  @Override
  @Transactional(readOnly = true)
  public List<Task> getAllByUserId(final long userId) {
    return taskRepository.findAllByUserId(userId);
  }

  @Override
  @Transactional
  @CachePut(value = "TaskService::getById", key = "#task.taskId")
  public Task update(final Task task) {
    if (task.getStatus() == null) {
      task.setStatus(Status.TODO);
    }

    taskRepository.save(task);
    return task;
  }

  @Override
  @Transactional
  @Cacheable(value = "TaskService::getById", condition = "#task.taskId!=null", key = "#task.taskId")
  public Task create(final Task task, final long userId) {
    if (task.getStatus() == null) {
      task.setStatus(Status.TODO);
    }
    taskRepository.save(task);
    taskRepository.assignTask(userId, task.getTaskId());
    return task;
  }

  @Override
  @Transactional
  @CacheEvict(value = "TaskService::getById", key = "#taskId")
  public void delete(final long taskId) {
    taskRepository.deleteById(taskId);
  }


  @Override
  @Transactional
  @CacheEvict(value = "TaskService::getById", key = "#taskId")
  public void uploadImage(final long taskId, final TaskImage image) {
    Task task = getById(taskId);
    String filename = imageService.upload(image);
    task.getImages().add(filename);
    taskRepository.save(task);
  }
}
