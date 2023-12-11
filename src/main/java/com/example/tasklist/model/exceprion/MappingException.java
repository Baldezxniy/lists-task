package com.example.tasklist.model.exceprion;

public class MappingException extends RuntimeException {
  public MappingException() {
  }

  public MappingException(String message) {
    super(message);
  }

  public MappingException(String message, Throwable cause) {
    super(message, cause);
  }

  public MappingException(Throwable cause) {
    super(cause);
  }
}
