package com.example.tasklist.repositories.queries;

public class TaskQueries {
  public static final String FIND_BY_ID = """ 
          SELECT t.task_id         as task_id,
                 t.title           as task_title,
                 t.description     as task_description,
                 t.expiration_date as task_expiration_date,
                 t.status          as task_status
          FROM tasks t
          WHERE task_id = ?
          """;

  public static final String FIND_ALL_BY_USER_ID = """
          SELECT t.task_id         as task_id,
                 t.title           as task_title,
                 t.description     as tasK_description,
                 t.expiration_date as task_expiration_date,
                 t.status          as task_status
          FROM tasks t
          JOIN users_tasks ut on t.task_id = ut.task_id
          WHERE ut.user_id = ?
          """;

  public static final String ASSIGN = """
          INSERT INTO users_tasks (task_id, user_id)
          VALUES (?, ?)
          """;

  public static final String UPDATE = """
          UPDATE tasks
          SET
                 title = ?,
                 description = ?,
                 expiration_date = ?,
                 status = ?
          WHERE task_id = ?
          """;

  public static final String CREATE = """
          INSERT INTO tasks (title, description, expiration_date, status)
          VALUES (?, ?, ?, ?)
          """;

  public static final String DELETE = """
          DELETE FROM tasks
          WHERE task_id = ?
          """;
}
