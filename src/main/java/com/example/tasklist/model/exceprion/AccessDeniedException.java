package com.example.tasklist.model.exceprion;

/**
 * Доступ к данным запрещен
 */
public class AccessDeniedException extends RuntimeException {
  public AccessDeniedException() {
  }

  public AccessDeniedException(String message) {
    super(message);
  }

  public AccessDeniedException(String message, Throwable cause) {
    super(message, cause);
  }

  public AccessDeniedException(Throwable cause) {
    super(cause);
  }
}
