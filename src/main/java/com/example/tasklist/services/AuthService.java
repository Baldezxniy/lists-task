package com.example.tasklist.services;

import com.example.tasklist.web.dto.auth.JwtRequest;
import com.example.tasklist.web.dto.auth.JwtResponse;

public interface AuthService {
  JwtResponse login(JwtRequest loginRequest);

  JwtResponse refresh(String refreshToken);
}
