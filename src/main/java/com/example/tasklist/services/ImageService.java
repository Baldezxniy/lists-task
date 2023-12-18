package com.example.tasklist.services;

import com.example.tasklist.domain.task.TaskImage;

public interface ImageService {
  String upload(TaskImage image);
}
