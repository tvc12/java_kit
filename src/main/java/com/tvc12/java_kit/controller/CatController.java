package com.tvc12.java_kit.controller;

import io.vertx.core.Handler;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class CatController implements Controller {

  @Override
  public String getCurrentPath() {
    return "/api/cat";
  }

  @Override
  public void configure(Router router) {
    router.get("/").handler(this::handleGetCat);
  }


  void handleGetCat(RoutingContext context) {

  }
}
