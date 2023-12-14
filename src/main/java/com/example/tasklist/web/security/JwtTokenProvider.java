package com.example.tasklist.web.security;

import com.example.tasklist.model.exceprion.AccessDeniedException;
import com.example.tasklist.model.user.Role;
import com.example.tasklist.model.user.User;
import com.example.tasklist.services.UserService;
import com.example.tasklist.services.props.JwtProperties;
import com.example.tasklist.web.dto.auth.JwtResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JwtTokenProvider {

  private final JwtProperties jwtProperties;

  private final UserDetailsService userDetailsService;
  private final UserService userService;
  private SecretKey key;

  @PostConstruct
  public void init() {
    String secret64 = Encoders.BASE64.encode(jwtProperties.getSecret().getBytes());
    System.out.println(secret64);

    this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret64));
  }

  public String createAccessToken(long userId, String username, Set<Role> roles) {

    Date now = new Date();
    Date validity = new Date(now.getTime() + jwtProperties.getAccess());

    return Jwts.
            builder()
            .claims().subject(username).add("user_id", userId).add("roles", resolveRoles(roles))
            .and().
            issuedAt(now).expiration(validity).signWith(key).compact();
  }

  private List<String> resolveRoles(Set<Role> roles) {
    return roles.stream().map(Enum::name).collect(Collectors.toList());
  }

  public String createRefreshToken(long userId, String username) {

    Date now = new Date();
    Date validity = new Date(now.getTime() + jwtProperties.getRefresh());
    return Jwts.builder()
            .claims().subject(username).add("user_id", userId)
            .and()
            .issuedAt(now).expiration(validity).signWith(key).compact();
  }

  public JwtResponse refreshToken(String refresh) {
    JwtResponse jwtResponse = new JwtResponse();
    if (!validateToken(refresh)) {
      throw new AccessDeniedException();
    }
    long userId = Long.parseLong(getId(refresh));
    User user = userService.getById(userId);

    jwtResponse.setId(userId);
    jwtResponse.setUsername(user.getUsername());
    jwtResponse.setAccessToken(createAccessToken(userId, user.getUsername(), user.getRoles()));
    jwtResponse.setRefreshToken(createRefreshToken(userId, user.getUsername()));

    return jwtResponse;
  }

  public boolean validateToken(String refresh) {
    Jws<Claims> claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(refresh);

    return !claims.getPayload().getExpiration().before(new Date());
  }

  private String getId(String refresh) {
    return Jwts.parser().verifyWith(key).build().parseSignedClaims(refresh).getPayload().get("user_id").toString();
  }

  public Authentication getAuthentication(String token) {
    String username = getUsername(token);
    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  private String getUsername(String token) {
    return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject();
  }
}
