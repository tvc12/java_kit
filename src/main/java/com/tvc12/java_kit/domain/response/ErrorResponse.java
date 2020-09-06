package com.tvc12.java_kit.domain.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.netty.handler.codec.http.HttpResponseStatus;

public class ErrorResponse {
  public String error;

  @JsonIgnore
  public HttpResponseStatus httpStatus;

  public Object data;

  public String errorMsg;

  public ErrorResponse(String error, HttpResponseStatus httpStatus, Object data, String errorMsg) {
    this.error = error;
    this.httpStatus = httpStatus;
    this.data = data;
    this.errorMsg = errorMsg;
  }
}
