package com.tvc12.java_kit.service;

import com.tvc12.java_kit.domain.exception.AppException;

public class AuthenServiceImpl implements AuthenService {
  @Override
  public String login(String user, String password, boolean rememberMe, long sessionTimeout) throws AppException {
    return null;
  }

  @Override
  public void logout(String sessionId) throws AppException {

  }

  @Override
  public String getUser(String sessionId) throws AppException {
    return null;
  }
}
