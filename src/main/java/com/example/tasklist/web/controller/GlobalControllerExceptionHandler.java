package com.example.tasklist.web.controller;

import com.example.tasklist.domain.exceprion.AccessDeniedException;
import com.example.tasklist.domain.exceprion.HttpErrorInfo;
import com.example.tasklist.domain.exceprion.ImageUploadExceprionException;
import com.example.tasklist.domain.exceprion.MappingException;
import com.example.tasklist.domain.exceprion.NotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {

  private static final Logger LOG = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

  @ExceptionHandler(NotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public HttpErrorInfo handleNotFound(final NotFoundException ex) {

    return createHttpErrorInfo(HttpStatus.NOT_FOUND, ex);
  }

  @ExceptionHandler(MappingException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public HttpErrorInfo handleMapping(final MappingException ex) {

    return createHttpErrorInfo(HttpStatus.INTERNAL_SERVER_ERROR, ex);
  }

  @ExceptionHandler(IllegalStateException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public HttpErrorInfo handleIllegalState(final IllegalStateException ex) {

    return createHttpErrorInfo(HttpStatus.BAD_REQUEST, ex);
  }

  @ExceptionHandler({AccessDeniedException.class, org.springframework.security.access.AccessDeniedException.class})
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public HttpErrorInfo handleAccessDenied(final Exception ex) {

    return createHttpErrorInfo(HttpStatus.FORBIDDEN, ex);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public HttpErrorInfo handleArgumentNotValid(final MethodArgumentNotValidException ex) {

    HttpErrorInfo errorInfo = createHttpErrorInfo(HttpStatus.BAD_REQUEST, ex);
    List<FieldError> errors = ex.getBindingResult().getFieldErrors();
    errorInfo.setErrors(errors.stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage)));
    return errorInfo;
  }

  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public HttpErrorInfo handleConstraintViolationException(final ConstraintViolationException ex) {

    HttpErrorInfo errorInfo = createHttpErrorInfo(HttpStatus.BAD_REQUEST, ex);
    errorInfo.setErrors(ex.getConstraintViolations().stream().collect(Collectors.toMap(
            violation -> violation.getPropertyPath().toString(),
            violation -> violation.getMessage()
    )));
    return errorInfo;
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public HttpErrorInfo handleConstraintViolationException(final Exception ex) {
    ex.printStackTrace();
    return createHttpErrorInfo(HttpStatus.INTERNAL_SERVER_ERROR, ex);
  }

  @ExceptionHandler(AuthenticationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public HttpErrorInfo handleAuthentication(final AuthenticationException ignored) {

    return createHttpErrorInfo(HttpStatus.INTERNAL_SERVER_ERROR, "Authentication failed");
  }

  @ExceptionHandler(ImageUploadExceprionException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public HttpErrorInfo handleImageUpload(final ImageUploadExceprionException ex) {

    return createHttpErrorInfo(HttpStatus.INTERNAL_SERVER_ERROR, ex);
  }

  private HttpErrorInfo createHttpErrorInfo(
          final HttpStatus httpStatus, final Exception ex) {

    final String message = ex.getMessage();

    LOG.debug("Returning HTTP status: {} for path: {}, message: {}", httpStatus, message);
    return new HttpErrorInfo(httpStatus, message);
  }

  private HttpErrorInfo createHttpErrorInfo(
          final HttpStatus httpStatus, final String message) {

    LOG.debug("Returning HTTP status: {} for path: {}, message: {}", httpStatus, message);
    return new HttpErrorInfo(httpStatus, message);
  }

}
