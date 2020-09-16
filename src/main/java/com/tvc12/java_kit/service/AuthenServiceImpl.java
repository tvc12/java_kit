package com.tvc12.java_kit.service;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.tvc12.java_kit.domain.exception.NotFoundException;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.authentication.AuthenticationProvider;
import io.vertx.ext.auth.shiro.impl.ShiroUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

public class AuthenServiceImpl implements AuthenService {
  @Inject
  private Provider<AuthenticationProvider> authenticationProvider;

  @Inject
  private SecurityManager securityManager;

  @Override
  public Future<String> login(String user, String password, boolean rememberMe, long sessionTimeout) {
    if (isLoginSuccess(user, password)) {
      // can put user info to object
      JsonObject authInfo = new JsonObject()
        .put("username", user)
        .put("password", password);
      return authenticationProvider.get().authenticate(authInfo).map((newData) -> {
        Subject subject = SecurityUtils.getSubject();

        System.out.println(String.format("authenticate:: %s", newData.principal()));
        Session session = subject.getSession(true);
        session.touch();
        return session.getId().toString();
      });
    } else {
      return Future.failedFuture(new NotFoundException("Account not exists"));
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
    final Subject currentUser = new Subject.Builder(this.securityManager).sessionId(sessionId).buildSubject();
    if (currentUser == null || !currentUser.isAuthenticated()) {
      return Future.failedFuture(new NotFoundException("Session not found"));
    }
    final Session session = currentUser.getSession(false);

    if (session != null) {
      session.touch();
    }
    System.out.println("getUser::4");
    final String username = currentUser.getPrincipal().toString();
    System.out.println(String.format("getUser:: %s", username));
    return Future.succeededFuture(username);
  }
}
