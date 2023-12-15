CREATE TABLE IF NOT EXISTS users
(
    user_id bigserial PRIMARY KEY,
    name varchar(255) NOT NULL,
    username varchar(255) NOT NULL,
    password varchar(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS tasks
(
    task_id bigserial PRIMARY KEY,
    title varchar(255) NOT NULL,
    description varchar(255) NULL,
    status varchar(255) NOT NULL,
    expiration_date timestamp NULL
);

CREATE TABLE IF NOT EXISTS users_tasks
(
    user_id bigint NOT NULL,
    task_id bigint NOT NULL,
    primary key (user_id, task_id),
    CONSTRAINT fk_users_tasks_users FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE ON UPDATE NO ACTION,
    CONSTRAINT fk_users_tasks_task FOREIGN KEY (task_id) REFERENCES tasks (task_id) ON DELETE CASCADE ON UPDATE NO ACTION
);

CREATE TABLE IF NOT EXISTS users_roles
(
    user_id bigint NOT NULL,
    role varchar(255) NOT NULL,
    PRIMARY KEY  (user_id, role),
    CONSTRAINT fk_users_roles_users FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE ON UPDATE NO ACTION
)