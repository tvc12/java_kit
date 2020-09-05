package com.tvc12.java_kit;

import com.tvc12.java_kit.controller.filter.CorsFilter;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class MainVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    Router router = Router.router(vertx);
    router.route().handler(CorsFilter.build());
    router.route().handler(BodyHandler.create());
    router.route().handler(req -> {
      req.response()
        .putHeader("content-type", "application/json")
        .end("{\"text\": \"Hello from Vert.x!\"}");
    });

    vertx.createHttpServer().requestHandler(router).listen(8888, http -> {
      if (http.succeeded()) {
        startPromise.complete();
        System.out.println("HTTP server started on port 8888");
      } else {
        startPromise.fail(http.cause());
      }
    });
  }
}
