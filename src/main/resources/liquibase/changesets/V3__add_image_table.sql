CREATE TABLE IF NOT EXISTS tasks_images
(
    task_id bigint NOT NULL,
    image varchar(255) NOT NULL,
    CONSTRAINT fk_tasks_images_tasks FOREIGN KEY (task_id) REFERENCES tasks (task_id) ON DELETE CASCADE ON UPDATE NO ACTION
)