package com.tvc12.java_kit.controller;

import com.tvc12.java_kit.domain.exception.AppException;
import com.tvc12.java_kit.domain.response.AppResponse;
import com.tvc12.java_kit.domain.response.ErrorResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public abstract class Controller {
  public abstract String getCurrentPath();

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
    this.configResponse(context, HttpResponseStatus.OK).write(json);
  }

  protected <T> void error(RoutingContext context, Throwable exception) {
    ErrorResponse res;
    if (exception instanceof AppException) {
      res = ((AppException) exception).toResponse();
    } else {
      res = AppException.from(exception).toResponse();
    }
    String json = Json.encode(res);
    this.configResponse(context, res.httpStatus).write(json);
  }

  protected <T> void autoMapper(RoutingContext context, Resolver<T> resolver) {
    T data = resolver.resolve();
    if (data instanceof Future) {
      futureToResponse(context, (Future<T>) data);
    } else if (data instanceof Promise) {
      futureToResponse(context, ((Promise<T>) data).future());
    } else if (data instanceof Throwable) {
      this.error(context, (Throwable) data);
    } else {
      this.send(context, data);
    }
  }

  private <T> void futureToResponse(RoutingContext context, Future<T> future) {
    future.onSuccess(r -> this.send(context, r))
      .onFailure(ex -> this.error(context, ex));
  }

  private HttpServerResponse configResponse(RoutingContext context, HttpResponseStatus status) {
    return context.response()
      .setStatusCode(status.code())
      .putHeader("content-type", "application/json; charset=utf-8");
  }

}

interface Resolver<T> {
  T resolve();
}
