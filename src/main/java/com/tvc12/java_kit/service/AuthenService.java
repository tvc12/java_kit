package com.tvc12.java_kit.service;

import com.tvc12.java_kit.domain.exception.AppException;

public interface AuthenService {
  String login(String user, String password, boolean rememberMe, long sessionTimeout) throws AppException;

  void logout(String sessionId) throws AppException;

  String getUser(String sessionId) throws AppException;
}
