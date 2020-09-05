package com.tvc12.java_kit.controller;

import com.google.inject.Inject;
import com.tvc12.java_kit.domain.exception.AppException;
import com.tvc12.java_kit.domain.model.Cat;
import com.tvc12.java_kit.service.CatService;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class CatController extends Controller {
  @Inject
  private CatService catService;

  @Override
  public String getCurrentPath() {
    return "/api/cat";
  }

  @Override
  public void configure(Router router) {
    router.get("/:id").handler(this::handleGetCat);
    router.post("/").handler(this::handleAddCat);
  }

  private void handleAddCat(RoutingContext context) {
    Cat cat = context.getBodyAsJson().mapTo(Cat.class);
    this.autoMapper(context, () -> this.catService.add(cat));
  }

  private void handleGetCat(RoutingContext context) {
    String id = context.pathParam("id");
    this.autoMapper(context, () -> this.catService.get(id));
  }
}
