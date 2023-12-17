package com.example.tasklist.web.dto.task;

import com.example.tasklist.domain.task.Status;
import com.example.tasklist.web.dto.validation.OnCreate;
import com.example.tasklist.web.dto.validation.OnUpdate;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class TaskDto {

  @NotNull(message = "Id must be not null.", groups = {OnUpdate.class})
  private Long taskId;
  @NotNull(message = "Title must be not null.", groups = {OnCreate.class, OnUpdate.class})
  @Length(max = 255, message = "Title must be smaller than 255 symbols", groups = {OnCreate.class, OnUpdate.class} )
  private String title;

  @Length(max = 255, message = "Description must be smaller than 255 symbols", groups = {OnCreate.class, OnUpdate.class} )
  private String description;
  private Status status;
  @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
  private LocalDateTime expirationTime;
}
