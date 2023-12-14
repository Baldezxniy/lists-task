package com.example.tasklist.web.mappers;

import com.example.tasklist.model.task.Task;
import com.example.tasklist.web.dto.task.TaskDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {
  TaskDto entityToDto(Task task);

  List<TaskDto> entityToDto(List<Task> tasks);

  Task dtoToEntity(TaskDto taskDto);
}
