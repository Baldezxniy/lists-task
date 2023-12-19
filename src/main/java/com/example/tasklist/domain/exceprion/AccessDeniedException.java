package com.example.tasklist.domain.exceprion;

/**
 * Доступ к данным запрещен.
 */
public class AccessDeniedException extends RuntimeException {
  public AccessDeniedException() {
  }

  public AccessDeniedException(final String message) {
    super(message);
  }

  public AccessDeniedException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public AccessDeniedException(final Throwable cause) {
    super(cause);
  }
}
