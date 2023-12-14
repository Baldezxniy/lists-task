package com.example.tasklist.web.controller;

import com.example.tasklist.model.user.User;
import com.example.tasklist.web.dto.user.UserDto;
import com.example.tasklist.web.dto.validation.OnCreate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tasklist.repositories.AuthService;
import com.example.tasklist.services.UserService;
import com.example.tasklist.web.dto.auth.JwtRequest;
import com.example.tasklist.web.dto.auth.JwtResponse;
import com.example.tasklist.web.mappers.UserMapper;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthController {

  private final AuthService authService;
  private final UserService userService;
  private final UserMapper userMapper;

  @PostMapping("/login")
  public JwtResponse login(@Validated @RequestBody JwtRequest loginRequest) {
    return authService.login(loginRequest);
  }

  @PostMapping("/register")
  public UserDto register(@Validated(OnCreate.class) @RequestBody UserDto userDto) {
    User userEntity = userMapper.dtoToEntity(userDto);
    User createdUser = userService.create(userEntity);
    return userMapper.entityToDto(createdUser);
  }

  @PostMapping("/refresh")
  public JwtResponse refresh(@RequestBody String refreshToken) {
    return authService.refresh(refreshToken);
  }
}
