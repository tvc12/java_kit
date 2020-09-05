package com.tvc12.java_kit.domain.exception;

import com.tvc12.java_kit.domain.response.ErrorResponse;
import io.netty.handler.codec.http.HttpResponseStatus;

public abstract class AppException extends Exception {
  static final long serialVersionUID = 121218L;

  public String getError() {
    return error;
  }

  @Override
  public String getMessage() {
    return message;
  }

  public HttpResponseStatus getStatus() {
    return status;
  }

  protected String error;
  protected String message;
  protected HttpResponseStatus status;

  public AppException(String error, String message) {
    super(message);
    this.error = error;
    this.status = HttpResponseStatus.INTERNAL_SERVER_ERROR;
  }

  public AppException(String error) {
    super("");
    this.error = error;
    this.status = HttpResponseStatus.INTERNAL_SERVER_ERROR;
  }

  public AppException(String error, String message, HttpResponseStatus status) {
    super(message);
    this.error = error;
    this.status = status;
  }

  public static AppException from(Throwable exception) {
    return new InternalErrorException(exception.getMessage());
  }

  public ErrorResponse toResponse() {
    return new ErrorResponse(error, status, null, message);
  }

  static final String NOT_FOUND = "not_found";
  static final String INTERNAL_ERROR = "INTERNAL_ERROR";
}
