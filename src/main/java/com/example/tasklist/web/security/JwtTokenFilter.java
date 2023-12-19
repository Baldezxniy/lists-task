package com.example.tasklist.web.security;

import com.example.tasklist.domain.exceprion.NotFoundException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtTokenFilter extends GenericFilterBean {

  private final JwtTokenProvider jwtTokenProvider;

  @Override
  public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
          throws IOException, ServletException {
    String bearerToken = ((HttpServletRequest) request).getHeader("Authorization");
    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      bearerToken = bearerToken.substring(7);
    }

    if (bearerToken != null && jwtTokenProvider.validateToken(bearerToken)) {
      try {
        Authentication authentication = jwtTokenProvider.getAuthentication(bearerToken);
        if (authentication != null) {
          SecurityContextHolder.getContext().setAuthentication(authentication);
        }
      } catch (NotFoundException ignored) {
      }

    }

    chain.doFilter(request, response);
  }
}
