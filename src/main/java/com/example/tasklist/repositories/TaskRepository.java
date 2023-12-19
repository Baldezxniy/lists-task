package com.example.tasklist.repositories;

import com.example.tasklist.domain.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
  @Query(value = """
          SELECT t.task_id, t.title, t.description, t.status, t.expiration_date
          FROM tasks t
          JOIN users_tasks ut ON ut.task_id = t.task_id
          WHERE ut.user_id = :userId
          """, nativeQuery = true)
  List<Task> findAllByUserId(@Param("userId") long userId);
}
