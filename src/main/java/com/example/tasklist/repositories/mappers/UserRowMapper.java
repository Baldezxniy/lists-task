package com.example.tasklist.repositories.mappers;

import com.example.tasklist.domain.task.Task;
import com.example.tasklist.domain.user.Role;
import com.example.tasklist.domain.user.User;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserRowMapper {
  @SneakyThrows
  public static User mapRow(ResultSet resultSet) {
    System.out.println("MAP ROW");
    Set<Role> roles = new HashSet<>();

    while (resultSet.next()) {
      roles.add(Role.valueOf(resultSet.getString("user_role_role")));
    }
    System.out.println("ROLES " + roles);

    resultSet.beforeFirst();
    List<Task> tasks = TaskRowMapper.mapRows(resultSet);

    System.out.println("TASK " + tasks);
    resultSet.beforeFirst();
    if (resultSet.next()) {
      User user = new User();
      user.setUserId(resultSet.getLong("user_id"));
      user.setName(resultSet.getString("user_name"));
      user.setUsername(resultSet.getString("user_username"));
      user.setPassword(resultSet.getString("user_password"));
      user.setRoles(roles);
      user.setTasks(tasks);
      System.out.println("USER ROW " + user.getPassword());
      return user;
    }

    return null;
  }
}
