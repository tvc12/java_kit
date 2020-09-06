package com.tvc12.java_kit.domain.exception;

import com.tvc12.java_kit.domain.response.ErrorResponse;
import io.netty.handler.codec.http.HttpResponseStatus;

public abstract class AppException extends Exception {
  static final long serialVersionUID = 121218L;
  static final String NOT_FOUND = "not_found";
  static final String INTERNAL_ERROR = "INTERNAL_ERROR";
  protected String error;
  protected String message;
  protected HttpResponseStatus status;
  public AppException(String error, String message) {
    super(message);
    this.message = "";
    this.error = error;
    this.status = HttpResponseStatus.INTERNAL_SERVER_ERROR;
  }

  public AppException(String error) {
    super("");
    this.message = "";
    this.error = error;
    this.status = HttpResponseStatus.INTERNAL_SERVER_ERROR;
  }

  public AppException(String error, String message, HttpResponseStatus status) {
    super(message);
    this.message = message;
    this.error = error;
    this.status = status;
  }

  public static AppException from(Throwable exception) {
    return new InternalErrorException(exception.getMessage());
  }

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

  public ErrorResponse toResponse() {
    return new ErrorResponse(error, status, null, message);
  }
}
