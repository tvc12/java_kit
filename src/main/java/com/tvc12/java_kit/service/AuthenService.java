package com.tvc12.java_kit.service;


import io.vertx.core.Future;

public interface AuthenService {
  Future<String> login(String user, String password, boolean rememberMe, long sessionTimeout);

  Future<Boolean> logout(String sessionId);

  Future<String> getUser(String sessionId);
}
