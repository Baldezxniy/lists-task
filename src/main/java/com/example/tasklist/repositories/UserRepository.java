package com.example.tasklist.repositories;

import com.example.tasklist.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);

  @Query(value = """
          SELECT
          u.user_id as user_id,
          u.name as name,
          u.username as username,
          u.password as password
          FROM users_tasks ut
          JOIN users u ON u.user_id = ut.user_id
          WHERE ut.task_id = :taskId
          """, nativeQuery = true)
  Optional<User> findTaskAuthor(@Param("taskId") long taskId);

  @Query(value = """
          SELECT exists(
                           SELECT 1 FROM users_tasks
                           WHERE user_id = :userId
                           AND task_id = :taskId
          )
          """, nativeQuery = true)
  boolean isTaskOwner(@Param("userId") long userId, @Param("taskId") long taskId);
}
