package com.example.tasklist.repositories.impl;

import com.example.tasklist.config.DataSourceConfig;
import com.example.tasklist.domain.exceprion.MappingException;
import com.example.tasklist.domain.task.Task;
import com.example.tasklist.repositories.TaskRepository;
import com.example.tasklist.repositories.mappers.TaskRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;
import java.util.Optional;

import static com.example.tasklist.repositories.queries.TaskQueries.*;

@Repository
@RequiredArgsConstructor
public class TaskRepositoryImpl implements TaskRepository {

  private final DataSourceConfig dataSourceConfig;


  @Override
  public Optional<Task> findById(long taskId) {
    try {
      Connection connection = dataSourceConfig.getConnection();
      PreparedStatement statement = connection.prepareStatement(FIND_BY_ID);

      statement.setLong(1, taskId);

      try (ResultSet rs = statement.executeQuery()) {
        return Optional.ofNullable(TaskRowMapper.mapRow(rs));
      }

    } catch (SQLException e) {
      throw new MappingException("Error while finding task by id.");
    }
  }

  @Override
  public List<Task> findAllByUserId(long userId) {
    try {
      Connection connection = dataSourceConfig.getConnection();
      PreparedStatement statement = connection.prepareStatement(FIND_ALL_BY_USER_ID);

      statement.setLong(1, userId);

      try (ResultSet rs = statement.executeQuery()) {
        return TaskRowMapper.mapRows(rs);
      }

    } catch (SQLException e) {
      throw new MappingException("Error while finding all by user by id.");
    }
  }

  @Override
  public void assignToUserById(long taskId, long userId) {
    try {
      Connection connection = dataSourceConfig.getConnection();
      PreparedStatement statement = connection.prepareStatement(ASSIGN);

      statement.setLong(1, taskId);
      statement.setLong(2, userId);
      statement.executeUpdate();

    } catch (SQLException e) {
      throw new MappingException("Error while updating user.");
    }
  }

  @Override
  public void update(Task task) {
    try {
      Connection connection = dataSourceConfig.getConnection();
      PreparedStatement statement = connection.prepareStatement(UPDATE);

      statement.setString(1, task.getTitle());
      if (task.getDescription() == null) {
        statement.setNull(2, Types.VARCHAR);
      } else {
        statement.setString(2, task.getDescription());
      }

      if (task.getExpirationTime() == null) {
        statement.setNull(3, Types.TIMESTAMP);
      } else {
        statement.setTimestamp(3, Timestamp.valueOf(task.getExpirationTime()));
      }
      statement.setString(4, task.getStatus().name());
      statement.setLong(5, task.getTaskId());

      statement.executeUpdate();
    } catch (SQLException e) {
      throw new MappingException("Error while assigning to user.");
    }
  }

  @Override
  public void create(Task task) {
    try {
      Connection connection = dataSourceConfig.getConnection();
      PreparedStatement statement = connection.prepareStatement(CREATE, PreparedStatement.RETURN_GENERATED_KEYS);

      System.out.println("TASK IN CREATE " + task.getTitle());
      statement.setString(1, task.getTitle());

      if (task.getDescription() == null) {
        statement.setNull(2, Types.VARCHAR);
      } else {
        statement.setString(2, task.getDescription());
      }

      if (task.getExpirationTime() == null) {
        statement.setNull(3, Types.TIMESTAMP);
      } else {
        statement.setTimestamp(3, Timestamp.valueOf(task.getExpirationTime()));
      }

      statement.setString(4, task.getStatus().name());
      System.out.println("----------");
      statement.executeUpdate();
      System.out.println("AFTER ----------");
      try (ResultSet rs = statement.getGeneratedKeys()) {
        rs.next();
        task.setTaskId(rs.getLong(1));
        System.out.println("TASK ID: " + task.getTaskId());
      }
    } catch (SQLException e) {
      throw new MappingException("Error while creating task.");
    }
  }

  @Override
  public void delete(long taskId) {
    try {
      Connection connection = dataSourceConfig.getConnection();
      PreparedStatement statement = connection.prepareStatement(DELETE);

      statement.setLong(1, taskId);
      statement.executeUpdate();
    } catch (SQLException e) {
      throw new MappingException("Error while deleted to task.");
    }
  }
}
