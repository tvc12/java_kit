package com.tvc12.java_kit.module;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Stage;
import com.google.inject.util.Modules;
import com.tvc12.java_kit.controller.Controller;
import com.tvc12.java_kit.controller.filter.CorsFilter;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public abstract class AbstractApp extends AbstractVerticle {

  public Injector injector;

  protected abstract Module[] modules();

  protected Router initRoute() {
    Router router = Router.router(vertx);
    router.route().handler(CorsFilter.build());
    router.route().handler(BodyHandler.create());
    router.route().consumes("application/json");
    router.route().produces("application/json");

    router.route().failureHandler(context -> {
      Throwable ex = context.failure();
      Controller.error(context, ex);
    });

    router.route().handler(ctx -> {
      ctx.response()
        .putHeader("Content-Type", "application/json; charset=utf-8");
      ctx.next();
    });
    return router;
  }

  protected abstract void setupRouter(Router router);

  protected Module overrideModule(Module[] modules) {
    if (modules.length == 1) return modules[0];
    Module module = modules[0];
    for (int i = 1; i < modules.length; ++i) {
      Module m = modules[0];
      module = Modules.override(module).with(m);
    }
    return module;
  }

  private Injector initModules() {
    Stage stage = this.getStage(System.getenv("MODE"));
    Module[] modules = new Module[]{overrideModule(modules())};
    return Guice.createInjector(stage, modules);
  }

  private Stage getStage(String mode) {
    if ("production".equals(mode)) {
      return Stage.PRODUCTION;
    } else {
      return Stage.DEVELOPMENT;
    }
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    this.injector = initModules();
    Router router = initRoute();
    setupRouter(router);

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
