package com.tvc12.java_kit.domain.response;

public class AppResponse<T> {
  boolean success;
  T data;

  public AppResponse(boolean success, T data) {
    this.success = success;
    this.data = data;
  }
}
