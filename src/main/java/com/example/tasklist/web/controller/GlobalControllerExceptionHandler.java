package com.example.tasklist.web.controller;

import com.example.tasklist.domain.exceprion.AccessDeniedException;
import com.example.tasklist.domain.exceprion.HttpErrorInfo;
import com.example.tasklist.domain.exceprion.MappingException;
import com.example.tasklist.domain.exceprion.NotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {

  private static final Logger LOG = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

  @ExceptionHandler(NotFoundException.class)
  @ResponseStatus(NOT_FOUND)
  public HttpErrorInfo handleNotFound(NotFoundException ex) {

    return createHttpErrorInfo(NOT_FOUND, ex);
  }

  @ExceptionHandler(MappingException.class)
  @ResponseStatus(INTERNAL_SERVER_ERROR)
  public HttpErrorInfo handleMapping(MappingException ex) {

    return createHttpErrorInfo(INTERNAL_SERVER_ERROR, ex);
  }

  @ExceptionHandler(IllegalStateException.class)
  @ResponseStatus(BAD_REQUEST)
  public HttpErrorInfo handleIllegalState(IllegalStateException ex) {

    return createHttpErrorInfo(BAD_REQUEST, ex);
  }

  @ExceptionHandler({AccessDeniedException.class, org.springframework.security.access.AccessDeniedException.class})
  @ResponseStatus(FORBIDDEN)
  public HttpErrorInfo handleAccessDenied(Exception ex) {

    return createHttpErrorInfo(FORBIDDEN, ex);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(BAD_REQUEST)
  public HttpErrorInfo handleArgumentNotValid(MethodArgumentNotValidException ex) {

    HttpErrorInfo errorInfo = createHttpErrorInfo(BAD_REQUEST, ex);
    List<FieldError> errors = ex.getBindingResult().getFieldErrors();
    errorInfo.setErrors(errors.stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage)));
    return errorInfo;
  }

  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(BAD_REQUEST)
  public HttpErrorInfo handleConstraintViolationException(ConstraintViolationException ex) {

    HttpErrorInfo errorInfo = createHttpErrorInfo(BAD_REQUEST, ex);
    errorInfo.setErrors(ex.getConstraintViolations().stream().collect(Collectors.toMap(
            violation -> violation.getPropertyPath().toString(),
            violation -> violation.getMessage()
    )));
    return errorInfo;
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(INTERNAL_SERVER_ERROR)
  public HttpErrorInfo handleConstraintViolationException(Exception ex) {

    return createHttpErrorInfo(INTERNAL_SERVER_ERROR, ex);
  }

  private HttpErrorInfo createHttpErrorInfo(
          HttpStatus httpStatus, Exception ex) {

    final String message = ex.getMessage();

    LOG.debug("Returning HTTP status: {} for path: {}, message: {}", httpStatus, message);
    return new HttpErrorInfo(httpStatus, message);
  }

}
