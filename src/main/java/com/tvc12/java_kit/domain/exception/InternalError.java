package com.tvc12.java_kit.domain.exception;

import io.netty.handler.codec.http.HttpResponseStatus;

public class InternalError extends AppException {
  public InternalError(String message) {
    super(message, "", HttpResponseStatus.INTERNAL_SERVER_ERROR);
  }
}
