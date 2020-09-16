package com.tvc12.java_kit.module;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
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
import io.vertx.core.json.jackson.DatabindCodec;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.sstore.SessionStore;


public abstract class AbstractApp extends AbstractVerticle {
  protected Injector injector;
  protected Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
  protected int port;

  public AbstractApp() {
    String rawPort = System.getenv("PORT");
    if (rawPort != null)
      this.port = Integer.parseInt(rawPort);
    else {
      this.port = 12128;
    }
  }

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

  protected void setupJson() {
    ObjectMapper mapper = DatabindCodec.mapper();
    ObjectMapper prettyMapper = DatabindCodec.prettyMapper();
    configMapper(prettyMapper);
    configMapper(mapper);
  }

  protected void configMapper(ObjectMapper mapper) {
    mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    mapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  }

  protected abstract void setupRouter(Router router);

  protected Module overrideModule(Module[] modules) {
    if (modules.length == 1) return modules[0];
    Module module = modules[0];
    for (int i = 1; i < modules.length; ++i) {
      Module m = modules[i];
      module = Modules.override(module).with(m);
    }
    return module;
  }

  private Injector initModules() {
    Stage stage = this.getStage(System.getenv("MODE"));
    Module[] modules = new Module[]{
      new HibernateModule(),
      new VertxModule(vertx),
      overrideModule(modules()),
    };
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
    setupJson();
    Router router = initRoute(vertx);
    setupRouter(router);

    router.getRoutes().forEach(route -> {
      logger.info(String.format("register:: route:: %s Methods:: %s", route.getPath(), route.methods()));
    });

    vertx.createHttpServer().requestHandler(router).listen(port, http -> {
      if (http.succeeded()) {
        startPromise.complete();
        logger.info(String.format("HTTP server started on port %s", port));
      } else {
        startPromise.fail(http.cause());
      }
    });
  }
}
