package com.example.tasklist.web.security;

import com.example.tasklist.domain.exceprion.AccessDeniedException;
import com.example.tasklist.domain.user.Role;
import com.example.tasklist.domain.user.User;
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

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
  private Key key;

  @PostConstruct
  public void init() {
    String secret64 = Encoders.BASE64.encode(jwtProperties.getSecret().getBytes());

    this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret64));
  }

  public String createAccessToken(
          final Long userId,
          final String username,
          final Set<Role> roles
  ) {
    Claims claims = Jwts.claims().setSubject(username);
    claims.put("id", userId);
    claims.put("roles", resolveRoles(roles));
    Instant validity = Instant.now()
            .plus(jwtProperties.getAccess(), ChronoUnit.HOURS);
    return Jwts.builder()
            .setClaims(claims)
            .setExpiration(Date.from(validity))
            .signWith(key)
            .compact();
  }

  private List<String> resolveRoles(final Set<Role> roles) {
    return roles.stream().map(Enum::name).collect(Collectors.toList());
  }

  public String createRefreshToken(final long userId, final String username) {

    Claims claims = Jwts.claims().setSubject(username);
    claims.put("id", userId);
    Instant validity = Instant.now()
            .plus(jwtProperties.getRefresh(), ChronoUnit.DAYS);
    return Jwts.builder()
            .setClaims(claims)
            .setExpiration(Date.from(validity))
            .signWith(key)
            .compact();
  }

  public JwtResponse refreshTokens(final String refresh) {
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

  public boolean validateToken(final String refresh) {
    Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key)
            .build().parseClaimsJws(refresh);

    return !claims.getBody().getExpiration().before(new Date());
  }

  private String getId(final String refresh) {
    return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(refresh).getBody().get("user_id").toString();
  }

  public Authentication getAuthentication(final String token) {
    String username = getUsername(token);
    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  private String getUsername(final String token) {
    return Jwts
            .parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
  }
}
