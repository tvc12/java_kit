package com.tvc12.java_kit.domain.response;

public class AppResponse<T> {
  public boolean success;

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
