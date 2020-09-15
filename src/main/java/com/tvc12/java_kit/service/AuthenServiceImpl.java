package com.tvc12.java_kit.service;

import com.google.inject.Inject;
import com.tvc12.java_kit.domain.exception.NotFoundException;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.authentication.AuthenticationProvider;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

public class AuthenServiceImpl implements AuthenService {
  @Inject
  AuthenticationProvider authenticationProvider;

  @Inject
  SecurityManager securityManager;

  @Override
  public Future<String> login(String user, String password, boolean rememberMe, long sessionTimeout) {
    if (isLoginSuccess(user, password)) {
      // can put user info to object
      JsonObject authInfo = new JsonObject()
        .put("username", user)
        .put("password", password)
        .put("user_id", "10000412345");
      return authenticationProvider.authenticate(authInfo).map((newData) -> {
        Session session = SecurityUtils.getSubject().getSession(false);
        session.touch();
        return session.getId().toString();
      });
    } else {
      return Future.failedFuture(new NotFoundException());
    }
  }

  private boolean isLoginSuccess(String user, String password) {
    // Connect with database and check in here
    return "admin".equals(user) && "123456".equals(password);
  }

  @Override
  public Future<Boolean> logout(String sessionId) {
    new Subject.Builder().sessionId(sessionId).buildSubject().logout();
    return Future.succeededFuture(true);
  }

  @Override
  public Future<String> getUser(String sessionId) {
    final Session session = new Subject.Builder().sessionId(sessionId).buildSubject().getSession(false);
    final String username = session.getAttribute("user_id").toString();
    return Future.succeededFuture(username);
  }
}
