package com.example.tasklist.repositories.queries;

public class UserQueries {
  public static final String FIND_BY_ID = """ 
          SELECT u.user_id   
                  as user_id,
                  u.name            as user_name,
                  u.username        as user_username,
                  u.password        as user_password,
                  ur.role               as user_role_role,
                  t.task_id         as task_id,
                  t.title           as task_title,
                  t.description     as task_description,
                  t.expiration_date as task_expiration_date,
                  t.status          as task_status
          FROM users u
                    LEFT JOIN users_roles ur on u.user_id = ur.user_id
                    LEFT JOIN users_tasks ut on u.user_id = ut.user_id
                    LEFT JOIN tasks t on ut.task_id = t.task_id
          WHERE u.user_id = ?
          """;

  public static final String FIND_BY_USERNAME = """
          SELECT u.user_id     
                  as user_id,
                  u.name            as user_name,
                  u.username        as user_username,
                  u.password        as user_password,
                  ur.role           as user_role_role,
                  t.task_id         as task_id,
                  t.title           as task_title,
                  t.description     as task_description,
                  t.expiration_date as task_expiration_date,
                  t.status          as task_status
          FROM users u
                    LEFT JOIN users_roles ur on u.user_id = ur.user_id
                    LEFT JOIN users_tasks ut on u.user_id = ut.user_id
                    LEFT JOIN tasks t on ut.task_id = t.task_id
          WHERE u.username = ?
          """;

  public static final String UPDATE = """
          UPDATE users
          SET
                 name = ?,
                 username = ?,
                 password = ?,
          WHERE user_id = ?
          """;

  public static final String CREATE = """
          INSERT INTO users (name, username, password)
          VALUES (?, ?, ?)
          """;

  public static final String INSERT_USER_ROLE = """
          INSERT INTO users_roles (user_id, role)
          VALUES (?, ?)
          """;

  public static final String IS_TASK_OWNER = """
          SELECT exists(
                           SELECT 1 FROM users_tasks
                           WHERE user_id = ?
                           AND task_id = ?
          )
          """;

  public static final String DELETE = """
          DELETE FROM users
          WHERE user_id = ?
          """;
}
