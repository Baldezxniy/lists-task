package com.example.tasklist.web.security;

import com.example.tasklist.domain.user.User;
import com.example.tasklist.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {
  private final UserService userService;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    System.out.println("USER_DETAILS");
    User user = userService.getByUsername(username);
    System.out.println(user.getUsername() + " " + user.getPassword());
    return JwtEntityFactory.create(user);
  }
}
