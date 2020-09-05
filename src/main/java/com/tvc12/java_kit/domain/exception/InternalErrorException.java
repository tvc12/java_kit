package com.tvc12.java_kit.domain.exception;

import io.netty.handler.codec.http.HttpResponseStatus;

public class InternalErrorException extends AppException {
  public InternalErrorException(String message) {
    super(AppException.INTERNAL_ERROR, message, HttpResponseStatus.INTERNAL_SERVER_ERROR);
  }

  public InternalErrorException() {
    super(AppException.INTERNAL_ERROR, "", HttpResponseStatus.INTERNAL_SERVER_ERROR);
  }
}
