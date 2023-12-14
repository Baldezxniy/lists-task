package com.example.tasklist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@Component("example.com")
@EnableTransactionManagement
public class TaskListApplication {

  public static void main(String[] args) {
    SpringApplication.run(TaskListApplication.class, args);
  }

}
