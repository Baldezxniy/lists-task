package com.example.tasklist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableCaching
@EnableScheduling
@SpringBootApplication
@Component("example.com")
@EnableTransactionManagement
public class TaskListApplication {

  public static void main(final String[] args) {
    SpringApplication.run(TaskListApplication.class, args);
  }

}
