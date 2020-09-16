package com.tvc12.java_kit.controller;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.tvc12.java_kit.domain.request.LoginRequest;
import com.tvc12.java_kit.service.AuthenService;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

@Singleton
public class AuthenController extends Controller {
  private final String path = "/api/auth";

  @Inject
  private AuthenService authenService;

  @Override
  public void configure(Router router) {
    router.post(path).handler(this::login);

  }

  private void login(RoutingContext context) {
    this.autoMapper(context, () -> {
      LoginRequest loginRequest = context.getBodyAsJson().mapTo(LoginRequest.class);
      return this.authenService.login(loginRequest.username, loginRequest.password, true, 150000);
    });
  }
}
