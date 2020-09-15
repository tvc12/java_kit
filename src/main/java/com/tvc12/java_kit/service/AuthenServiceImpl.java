package com.tvc12.java_kit.service;

import com.google.inject.Inject;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.authentication.AuthenticationProvider;
import io.vertx.ext.auth.shiro.impl.ShiroUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import java.util.concurrent.Future;

public class AuthenServiceImpl implements AuthenService {
  @Inject
  AuthenticationProvider authenticationProvider;

  @Override
  public Future<String> login(String user, String password, boolean rememberMe, long sessionTimeout) {
    if (isLoginSuccess(user, password)) {
      // can put user info to object
      JsonObject authInfo = new JsonObject()
        .put("username", user)
        .put("password", password);
      authenticationProvider.authenticate(authInfo).onSuccess(user -> {

      });
    }

    return null;
  }

  private boolean isLoginSuccess(String user, String password) {
    return true;
  }

  @Override
  public Future<Boolean> logout(String sessionId) {
    return null;
  }

  @Override
  public Future<String> getUser(String sessionId) {
    return null;
  }
}
