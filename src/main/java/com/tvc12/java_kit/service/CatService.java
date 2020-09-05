package com.tvc12.java_kit.service;

import com.tvc12.java_kit.domain.exception.AppException;
import com.tvc12.java_kit.domain.model.Cat;

public interface CatService {
  Cat get(String id) throws AppException;

  Cat delete(String id) throws AppException;
}


