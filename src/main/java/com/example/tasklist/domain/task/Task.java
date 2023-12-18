package com.example.tasklist.domain.task;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "tasks")
public class Task implements Serializable {
  @Id
  @Column(name = "task_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long taskId;

  private String title;
  private String description;
  @Enumerated(value = EnumType.STRING)
  private Status status;
  @Column(name = "expiration_date")
  private LocalDateTime expirationDate;

  @Column(name = "image")
  @CollectionTable(name = "tasks_images", joinColumns = @JoinColumn(name = "task_id"))
  @ElementCollection
  private List<String> images;
}
