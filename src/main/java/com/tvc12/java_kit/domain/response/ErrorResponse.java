package com.tvc12.java_kit.domain.response;

import io.netty.handler.codec.http.HttpResponseStatus;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

public class ErrorResponse {
  public String error;

  @JsonIgnore
  public HttpResponseStatus httpStatus;

  public Object data;

  @JsonProperty("error_msg")
  public String errorMsg;

  public ErrorResponse(String error, HttpResponseStatus httpStatus, Object data, String errorMsg) {
    this.error = error;
    this.httpStatus = httpStatus;
    this.data = data;
    this.errorMsg = errorMsg;
  }
}
