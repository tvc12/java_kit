package com.tvc12.java_kit.service;

import com.tvc12.java_kit.domain.model.Cat;
import io.vertx.core.Future;

public interface CatService {
  Future<Cat> get(String id);

  Future<Cat> add(Cat cat);
}


