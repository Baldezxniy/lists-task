package com.example.tasklist.web.dto.task;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class TaskImageDto {
  @NotNull(message = "Image must be no null.")
  private MultipartFile file;
}
