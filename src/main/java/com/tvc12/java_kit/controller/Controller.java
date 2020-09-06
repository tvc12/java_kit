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
import io.vertx.core.json.JsonObject;
import io.vertx.core.parsetools.JsonParser;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public abstract class Controller {
  private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

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
    logger.info(String.format("\t\tsend:: %s", res.toString()));
    String json = JsonObject.mapFrom(res).encode();
    logger.info(String.format("\t\tsend::json:: %s", json));
    context.response().end(json);
  }

  public static <T> void error(RoutingContext context, Throwable exception) {
    ErrorResponse res;
    if (exception instanceof AppException) {
      res = ((AppException) exception).toResponse();
    } else {
      res = AppException.from(exception).toResponse();
    }
    String json = Json.encode(res);
    Controller.configResponse(context, res.httpStatus).end(json);
  }

  protected <T> void autoMapper(RoutingContext context, Resolver<T> resolver) {
    logger.info(String.format("autoMapper:: %s", context.currentRoute().getPath()));
    try {
      T data = resolver.resolve();
      if (data instanceof Future) {
        logger.debug("\t\t autoMapper:: Future");
        futureToResponse(context, (Future<T>) data);
      } else if (data instanceof Promise) {
        logger.debug("\t\t autoMapper:: Promise");
        futureToResponse(context, ((Promise<T>) data).future());
      } else if (data instanceof Throwable) {
        logger.debug("\t\t autoMapper:: Throwable");

        Controller.error(context, (Throwable) data);
      } else {
        logger.info(String.format("\t\t autoMapper:: Can't mapped:: %s", data.toString()));
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

  static private HttpServerResponse configResponse(RoutingContext context, HttpResponseStatus status) {
    return context.response()
      .setStatusCode(status.code());
  }

}

