package com.example.tasklist.web.mappers;

import com.example.tasklist.domain.user.User;
import com.example.tasklist.web.dto.user.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserMapper extends Mappable<User, UserDto> {
  @Override
  @Mappings({
          @Mapping(target = "roles", ignore = true),
          @Mapping(target = "tasks", ignore = true)
  })
  User dtoToEntity(UserDto dto);
}
