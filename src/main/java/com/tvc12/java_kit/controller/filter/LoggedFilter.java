package com.tvc12.java_kit.controller.filter;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.tvc12.java_kit.domain.exception.NotFoundException;
import com.tvc12.java_kit.service.AuthenService;
import io.vertx.ext.web.RoutingContext;

@Singleton
public class LoggedFilter {

  @Inject
  private AuthenService authenService;

  public void checkLogin(RoutingContext context) {
    // Support cookie by syntax:: context.cookieMap()
    final String token = context.request().getHeader("token");
    if (token != null) {
      System.out.println(String.format("Token:: %s", token));
    }
    this.authenService.getUser(token)
      .onSuccess((userId) -> {
        System.out.println("getUser::onSuccess");
        System.out.println(userId);
        if (userId != null) {
          context.next();
        } else {
          context.fail(new NotFoundException("User not found"));
        }
      })
      .onFailure((ex) -> {
        context.fail(ex);
      });
  }
}
