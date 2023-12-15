package com.example.tasklist.repositories.impl;

import com.example.tasklist.config.DataSourceConfig;
import com.example.tasklist.domain.exceprion.MappingException;
import com.example.tasklist.domain.user.Role;
import com.example.tasklist.domain.user.User;
import com.example.tasklist.repositories.UserRepository;
import com.example.tasklist.repositories.mappers.UserRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static com.example.tasklist.repositories.queries.UserQueries.*;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

  private final DataSourceConfig dataSourceConfig;

  @Override
  public Optional<User> findById(long userId) {
    try {
      Connection connection = dataSourceConfig.getConnection();
      PreparedStatement statement = connection.prepareStatement(FIND_BY_ID,
              ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

      statement.setLong(1, userId);
      try (ResultSet rs = statement.executeQuery()) {
        return Optional.ofNullable(UserRowMapper.mapRow(rs));
      }

    } catch (SQLException e) {
      throw new MappingException("Exception while finding user by id");
    }
  }

  @Override
  public Optional<User> findByUsername(String username) {

    System.out.println("FIND USER BY " + username);
    try {
      Connection connection = dataSourceConfig.getConnection();
      System.out.println("CONNECTION");
      PreparedStatement statement = connection.prepareStatement(FIND_BY_USERNAME,
              ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

      statement.setString(1, username);
      try (ResultSet rs = statement.executeQuery()) {
        return Optional.ofNullable(UserRowMapper.mapRow(rs));
      }

    } catch (SQLException e) {
      throw new MappingException("Exception while finding user by username");
    }
  }

  @Override
  public void update(User user) {
    try {
      Connection connection = dataSourceConfig.getConnection();
      PreparedStatement statement = connection.prepareStatement(UPDATE);

      statement.setLong(1, user.getUserId());
      statement.setString(2, user.getUsername());
      statement.setString(3, user.getPassword());
      statement.setLong(4, user.getUserId());
    } catch (SQLException e) {
      throw new MappingException("Exception while updating user.");
    }
  }

  @Override
  public void create(User user) {
    try {
      Connection connection = dataSourceConfig.getConnection();
      PreparedStatement statement = connection.prepareStatement(CREATE, PreparedStatement.RETURN_GENERATED_KEYS);

      System.out.println("CREATE USER");
      System.out.println(user.getName());
      System.out.println(user.getUsername());
      System.out.println(user.getPassword());
      statement.setString(1, user.getName());
      statement.setString(2, user.getUsername());
      statement.setString(3, user.getPassword());
      System.out.println("NORMAL");
      statement.executeUpdate();

      System.out.println("NORMAL 2");
      try (ResultSet rs = statement.getGeneratedKeys()) {
        rs.next();
        user.setUserId(rs.getLong(1));
        System.out.println(user.getUserId() + " IDDD");
      }
    } catch (SQLException e) {
      throw new MappingException("Exception while creating user.");
    }
  }

  @Override
  public void insertUserRole(long userId, Role role) {
    try {
      Connection connection = dataSourceConfig.getConnection();
      PreparedStatement statement = connection.prepareStatement(INSERT_USER_ROLE);

      statement.setLong(1, userId);
      statement.setString(2, role.name());
      statement.executeUpdate();
    } catch (SQLException e) {
      throw new MappingException("Exception while inserting user role user.");
    }
  }

  @Override
  public boolean isTaskOwner(long userId, long taskId) {
    try {
      Connection connection = dataSourceConfig.getConnection();
      PreparedStatement statement = connection.prepareStatement(IS_TASK_OWNER);
      statement.setLong(1, userId);
      statement.setLong(1, taskId);
      try (ResultSet rs = statement.executeQuery()) {
        rs.next();
        return rs.getBoolean(1);
      }
    } catch (SQLException e) {
      throw new MappingException("Exception while checking if user is task owner.");
    }
  }

  @Override
  public void delete(long userId) {
    try {
      Connection connection = dataSourceConfig.getConnection();
      PreparedStatement statement = connection.prepareStatement(DELETE);
      statement.setLong(1, userId);
      statement.executeUpdate();
    } catch (SQLException e) {
      throw new MappingException("Exception while deleting user.");
    }
  }
}
