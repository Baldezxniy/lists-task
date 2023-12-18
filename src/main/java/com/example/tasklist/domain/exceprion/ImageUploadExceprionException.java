package com.example.tasklist.domain.exceprion;

/**
 * Ошибка при загрузке изображения
 */
public class ImageUploadExceprionException extends RuntimeException {
  public ImageUploadExceprionException() {
  }

  public ImageUploadExceprionException(String message) {
    super(message);
  }

  public ImageUploadExceprionException(String message, Throwable cause) {
    super(message, cause);
  }

  public ImageUploadExceprionException(Throwable cause) {
    super(cause);
  }
}
