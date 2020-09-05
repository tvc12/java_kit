package com.tvc12.java_kit.controller.filter;

import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.handler.CorsHandler;

public class CorsFilter {
  public static CorsHandler build() {
    return CorsHandler.create("*")
      .allowedMethod(HttpMethod.GET)
      .allowedMethod(HttpMethod.POST)
      .allowedMethod(HttpMethod.PUT)
      .allowedMethod(HttpMethod.DELETE)
      .allowedHeader("Access-Control-Allow-Method")
      .allowedHeader("Access-Control-Allow-Origin")
      .allowedHeader("Access-Control-Allow-Credentials")
      .allowedHeader("Content-Type");
  }
}
