package com.tvc12.java_kit.module;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Stage;
import com.google.inject.util.Modules;
import com.tvc12.java_kit.controller.Controller;
import com.tvc12.java_kit.controller.filter.CorsFilter;
import com.tvc12.java_kit.domain.exception.NotFoundException;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.sstore.SessionStore;


public abstract class AbstractApp extends AbstractVerticle {
  public Injector injector;
  protected Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

  protected abstract Module[] modules();

  protected Router initRoute(Vertx vertx) {
    Router router = Router.router(vertx);

    router.route()
      .handler(CorsFilter.build())
//      .handler(StaticHandler.create())
      .handler(BodyHandler.create())
      .handler(SessionHandler.create(SessionStore.create(vertx)))
      .failureHandler(context -> {
        Throwable ex = context.failure();
        if (ex instanceof NoSuchMethodError) {
          Controller.error(context, new NotFoundException("Route isn't exists"));
        } else {
          Controller.error(context, ex);
        }
      })
      .handler(ctx -> {
        logger.error("initRoute::handler");
        ctx.response().putHeader("Content-Type", "application/json; charset=utf-8");
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
    Injector injector = Guice.createInjector(stage, modules);
    injector.injectMembers(this);
    return injector;
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
    Router router = initRoute(vertx);
    setupRouter(router);

    router.getRoutes().forEach(route -> {
      logger.info(String.format("register:: route:: %s Methods:: %s", route.getPath(), route.methods()));
    });

    vertx.createHttpServer().requestHandler(router).listen(8888, http -> {
      if (http.succeeded()) {
        startPromise.complete();
        logger.info("HTTP server started on port 8888");
      } else {
        startPromise.fail(http.cause());
      }
    });
  }
}
