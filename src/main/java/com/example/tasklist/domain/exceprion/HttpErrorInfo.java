package com.example.tasklist.domain.exceprion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class HttpErrorInfo {
  private String message;
  private HttpStatus httpStatus;
  private ZonedDateTime timestamp;
  private Map<String, String> errors;

  public HttpErrorInfo(HttpStatus httpStatus, String message) {
    timestamp = ZonedDateTime.now();
    this.httpStatus = httpStatus;
    this.message = message;
  }
}
