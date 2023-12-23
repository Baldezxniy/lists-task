package com.example.tasklist.services.impl;

import com.example.tasklist.config.TestConfig;
import com.example.tasklist.domain.exceprion.NotFoundException;
import com.example.tasklist.domain.task.Status;
import com.example.tasklist.domain.task.Task;
import com.example.tasklist.domain.task.TaskImage;
import com.example.tasklist.repositories.TaskRepository;
import com.example.tasklist.services.ImageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@Import(TestConfig.class)
@ExtendWith(MockitoExtension.class)
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class TaskServiceImplTest {

  @MockBean
  private TaskRepository taskRepository;

  @MockBean
  private ImageService imageService;

  @Autowired
  private TaskServiceImpl taskService;

  @Test
  void getById() {
    long taskId = 1L;
    Task task = new Task();
    task.setTaskId(taskId);
    Mockito.when(taskRepository.findById(taskId))
            .thenReturn(Optional.of(task));
    Task testTask = taskService.getById(taskId);
    Mockito.verify(taskRepository).findById(taskId);
    Assertions.assertEquals(task, testTask);
  }

  @Test
  void getByIdWitNotExistingId() {
    long taskId = 1L;

    Mockito.when(taskRepository.findById(taskId))
            .thenReturn(Optional.empty());

    Assertions.assertThrows(NotFoundException.class,
            () -> taskService.getById(1L));

    Mockito.verify(taskRepository).findById(taskId);
  }

  @Test
  void getAllByUserId() {
    long userId = 1L;
    List<Task> tasks = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      tasks.add(new Task());
    }

    Mockito.when(taskRepository.findAllByUserId(userId))
            .thenReturn(tasks);

    List<Task> testTask = taskService.getAllByUserId(userId);

    Mockito.verify(taskRepository).findAllByUserId(userId);
    Assertions.assertEquals(tasks, testTask);
  }

  @Test
  void getSoonTasks() {
    Duration duration = Duration.ofHours(1);
    List<Task> tasks = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      tasks.add(new Task());
    }
    Mockito.when(taskRepository.findAllSoonTasks(Mockito.any(), Mockito.any()))
            .thenReturn(tasks);
    List<Task> testTasks = taskService.getAllSoonTasks(duration);
    Mockito.verify(taskRepository)
            .findAllSoonTasks(Mockito.any(), Mockito.any());
    Assertions.assertEquals(tasks, testTasks);
  }

  @Test
  void update() {
    Task task = new Task();
    task.setTaskId(1L);
    task.setTitle("title");
    task.setDescription("description");
    task.setStatus(Status.DONE);

    Task testTask = taskService.update(task);
    Mockito.verify(taskRepository).save(task);
    Assertions.assertEquals(task, testTask);
  }

  @Test
  void updateWithNewStatus() {
    Task task = new Task();
    task.setTaskId(1L);
    task.setTitle("title");
    task.setDescription("description");

    Task testTask = taskService.update(task);
    Mockito.verify(taskRepository).save(task);
    Assertions.assertEquals(Status.TODO, testTask.getStatus());
  }

  @Test
  void create() {
    long userId = 1L;
    long taskId = 1L;

    Task task = new Task();
    Mockito.doAnswer(invocation -> {
      Task savedTask = invocation.getArgument(0);
      savedTask.setTaskId(taskId);
      return savedTask;
    }).when(taskRepository).save(task);

    Task testTask = taskService.create(task, userId);
    Mockito.verify(taskRepository).save(task);
    Assertions.assertNotNull(testTask.getTaskId());
    Mockito.verify(taskRepository).assignTask(userId, taskId);
  }

  @Test
  void delete() {
    long taskId = 1L;
    taskService.delete(taskId);
    Mockito.verify(taskRepository).deleteById(taskId);
  }

  @Test
  void uploadImage() {
    long taskId = 1L;
    String imageName = "imageName";
    TaskImage taskImage = new TaskImage();

    Mockito.when(imageService.upload(taskImage))
            .thenReturn(imageName);

    taskService.uploadImage(taskId, taskImage);

    Mockito.verify(taskRepository).addImage(taskId, imageName);
  }
}
