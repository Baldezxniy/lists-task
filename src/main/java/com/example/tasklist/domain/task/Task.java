package com.example.tasklist.domain.task;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
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
  @ElementCollection(fetch = FetchType.EAGER)
  private List<String> images;
}
