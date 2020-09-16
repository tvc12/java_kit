package com.tvc12.java_kit.controller;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.tvc12.java_kit.controller.filter.LoggedFilter;
import com.tvc12.java_kit.domain.model.Cat;
import com.tvc12.java_kit.service.CatService;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

@Singleton
public class CatController extends Controller {
  private final String path = "/api/cat";
  @Inject
  private CatService catService;

  @Inject LoggedFilter loggedFilter;

  @Override
  public void configure(Router router) {
    router.get(path).handler(this::handleHelloWord);
    router.get(String.format("%s/:id", path)).handler(loggedFilter::checkLogin).handler(this::handleGetCat);
    router.post(path).handler(this::handleAddCat);
  }

  private void handleHelloWord(RoutingContext context) {
    this.autoMapper(context, () -> {
      JsonObject map = new JsonObject();
      map.put("query", "Xin Chao \uD83D\uDE0D\uD83D\uDE0D");
      return map;
    });
  }

  private void handleAddCat(RoutingContext context) {
    System.out.println("handleAddCat::");
    System.out.println(context.getBodyAsJson().toString());
    Cat cat = context.getBodyAsJson().mapTo(Cat.class);
    this.autoMapper(context, () -> this.catService.add(cat));
  }

  private void handleGetCat(RoutingContext context) {
    String id = context.pathParam("id");
    this.autoMapper(context, () -> this.catService.get(id));
  }
}
