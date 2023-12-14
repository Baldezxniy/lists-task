package com.example.tasklist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
@Component("example.com")
public class TaskListApplication {

  public static void main(String[] args) {
    SpringApplication.run(TaskListApplication.class, args);
  }

}
