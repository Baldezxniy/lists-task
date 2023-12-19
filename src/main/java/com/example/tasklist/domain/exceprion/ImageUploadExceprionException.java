package com.example.tasklist.domain.exceprion;

/**
 * Ошибка при загрузке изображения.
 */
public class ImageUploadExceprionException extends RuntimeException {
  public ImageUploadExceprionException() {
  }

  public ImageUploadExceprionException(final String message) {
    super(message);
  }

  public ImageUploadExceprionException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public ImageUploadExceprionException(final Throwable cause) {
    super(cause);
  }
}
