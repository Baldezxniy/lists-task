package com.example.tasklist.repositories;

import com.example.tasklist.domain.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

  @Modifying
  @Query(value = """
          INSERT INTO users_tasks (user_id, task_id)
          VALUES (:userId, :taskId)
          """, nativeQuery = true)
  void assignTask(@Param("userId") Long userId, @Param("taskId") Long taskId);

  @Modifying
  @Query(value = """
          INSERT INTO tasks_images (task_id, image)
          VALUES (:taskId, :fileName)
          """, nativeQuery = true)
  void addImage(@Param("taskId") Long taskId, @Param("fileName") String fileName);
}
