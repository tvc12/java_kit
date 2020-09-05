package com.tvc12.java_kit.domain.exception;

import io.netty.handler.codec.http.HttpResponseStatus;

public class NotFoundException extends AppException {
  public NotFoundException(String message) {
    super(AppException.NOT_FOUND, message, HttpResponseStatus.NOT_FOUND);
  }

  public NotFoundException() {
    super(AppException.NOT_FOUND, "", HttpResponseStatus.NOT_FOUND);
  }
}
