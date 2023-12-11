package com.example.tasklist.web.mappers;

import com.example.tasklist.model.task.Task;
import com.example.tasklist.web.dto.task.TaskDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {
  TaskDto etityToDto(Task task);

  List<TaskDto> etityToDto(List<Task> tasks);

  Task dtoToEntity(TaskDto taskDto);
}
