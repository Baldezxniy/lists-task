package com.example.tasklist.domain.task;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class Task implements Serializable {
  @Column(name = "task_id")
  private Long taskId;
  private String title;
  private String description;
  private Status status;
  private LocalDateTime expirationTime;
}
