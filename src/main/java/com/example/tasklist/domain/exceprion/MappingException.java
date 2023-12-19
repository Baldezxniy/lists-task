package com.example.tasklist.domain.exceprion;

public class MappingException extends RuntimeException {
  public MappingException() {
  }

  public MappingException(final String message) {
    super(message);
  }

  public MappingException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public MappingException(final Throwable cause) {
    super(cause);
  }
}
