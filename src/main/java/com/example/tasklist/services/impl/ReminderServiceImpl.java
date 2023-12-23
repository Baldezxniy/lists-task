package com.example.tasklist.services.impl;

import com.example.tasklist.domain.MailType;
import com.example.tasklist.domain.task.Task;
import com.example.tasklist.domain.user.User;
import com.example.tasklist.services.MailService;
import com.example.tasklist.services.Reminder;
import com.example.tasklist.services.TaskService;
import com.example.tasklist.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

@Service
@RequiredArgsConstructor
public class ReminderServiceImpl implements Reminder {

  private final TaskService taskService;
  private final UserService userService;
  private final MailService mailService;

  private final Duration DURATION = Duration.ofHours(1);

  @Override
//  @Scheduled(cron = "0 0 * * * *")
  @Scheduled(cron = "0 * * * * *")
  public void remindForTask() {
    List<Task> tasks = taskService.getAllSoonTasks(DURATION);
    tasks.forEach(task -> {
      User user = userService.getTaskAuthor(task.getTaskId());

      Properties properties = new Properties();
      properties.setProperty("task.title", task.getTitle());
      properties.setProperty("task.description", task.getDescription());

      mailService.sendEmail(user, MailType.REMINDER, properties);
    });
  }
}
