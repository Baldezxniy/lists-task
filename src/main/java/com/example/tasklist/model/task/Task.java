package com.example.tasklist.model.task;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Task {
  @Column(name = "task_id")
  private Long taskId;
  private String title;
  private String description;
  private Status status;
  private LocalDateTime expirationTime;
}
