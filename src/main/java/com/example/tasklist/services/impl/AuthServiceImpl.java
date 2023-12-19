package com.example.tasklist.services.impl;

import com.example.tasklist.domain.user.User;
import com.example.tasklist.services.AuthService;
import com.example.tasklist.services.UserService;
import com.example.tasklist.web.dto.auth.JwtRequest;
import com.example.tasklist.web.dto.auth.JwtResponse;
import com.example.tasklist.web.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final AuthenticationManager authenticationManager;
  private final UserService userService;
  private final JwtTokenProvider jwtTokenProvider;

  @Override
  public JwtResponse login(final JwtRequest loginRequest) {
    JwtResponse jwtResponse = new JwtResponse();

    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
    } catch (Exception e) {
      e.printStackTrace();
    }

    User user = userService.getByUsername(loginRequest.getUsername());

    jwtResponse.setId(user.getUserId());
    jwtResponse.setUsername(user.getUsername());
    jwtResponse.setAccessToken(jwtTokenProvider.createAccessToken(user.getUserId(), user.getUsername(), user.getRoles()));
    jwtResponse.setRefreshToken(jwtTokenProvider.createRefreshToken(user.getUserId(), user.getUsername()));

    return jwtResponse;
  }

  @Override
  public JwtResponse refresh(String refreshToken) {
    return jwtTokenProvider.refreshTokens(refreshToken);
  }
}
