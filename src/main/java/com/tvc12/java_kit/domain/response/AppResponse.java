package com.tvc12.java_kit.domain.response;

import org.codehaus.jackson.annotate.JsonProperty;

public class AppResponse<T> {
  @JsonProperty
  public boolean success;

  @JsonProperty
  public T data;

  public AppResponse(boolean success, T data) {
    this.success = success;
    this.data = data;
  }

  @Override
  public String toString() {
    return "AppResponse{" +
      "success=" + success +
      ", data=" + data +
      '}';
  }
}
