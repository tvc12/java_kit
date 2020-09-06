package com.tvc12.java_kit.controller;

import com.tvc12.java_kit.domain.Resolver;
import com.tvc12.java_kit.domain.exception.AppException;
import com.tvc12.java_kit.domain.response.AppResponse;
import com.tvc12.java_kit.domain.response.ErrorResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public abstract class Controller {
  private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

  public static <T> void error(RoutingContext context, Throwable exception) {
    System.out.println(String.format("Controller::error:: %s", exception.getMessage()));
    ErrorResponse res;
    if (exception instanceof AppException) {
      res = ((AppException) exception).toResponse();
    } else {
      res = AppException.from(exception).toResponse();
    }
    String json = Json.encode(res);
    Controller.configResponse(context, res.httpStatus).end(json);
  }

  static private HttpServerResponse configResponse(RoutingContext context, HttpResponseStatus status) {
    return context.response()
      .setStatusCode(status.code());
  }

  public abstract void configure(Router router);

  protected <T> AppResponse<T> toResponse(T data) {
    return new AppResponse<T>(true, data);
  }

  protected <T> void send(RoutingContext context, T data) {
    AppResponse<T> res;
    if (data instanceof AsyncResult) {
      res = toResponse(((AsyncResult<T>) data).result());
    } else {
      res = toResponse(data);
    }
    String json = Json.encode(res);
    context.response().end(json);
  }

  protected <T> void autoMapper(RoutingContext context, Resolver<T> resolver) {
    logger.info(String.format("autoMapper:: %s", context.currentRoute().getPath()));
    try {
      T data = resolver.resolve();
      if (data instanceof Future) {
        logger.info("\t\t autoMapper:: Future");
        futureToResponse(context, (Future<T>) data);
      } else if (data instanceof Promise) {
        logger.info("\t\t autoMapper:: Promise");
        futureToResponse(context, ((Promise<T>) data).future());
      } else if (data instanceof Throwable) {
        logger.info("\t\t autoMapper:: Throwable");

        Controller.error(context, (Throwable) data);
      } else {
        logger.info("\t\t autoMapper:: Raw data");
        this.send(context, data);
      }
    } catch (Throwable ex) {
      Controller.error(context, ex);
    }
  }

  private <T> void futureToResponse(RoutingContext context, Future<T> future) {
    future.onSuccess(r -> this.send(context, r))
      .onFailure(ex -> Controller.error(context, ex));
  }
}

