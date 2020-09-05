package com.tvc12.java_kit.controller;

import io.vertx.ext.web.Router;

public interface Controller {
  String getCurrentPath();

  void configure(Router router);
}
