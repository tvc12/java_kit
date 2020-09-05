package com.tvc12.java_kit.service;

import com.tvc12.java_kit.domain.exception.AppException;
import com.tvc12.java_kit.domain.model.Cat;
import io.vertx.core.Future;

public class CatTestService implements CatService {

  @Override
  public Future<Cat> get(String id) {
    Cat cat = new Cat(id, "Kitty", 12, "Mety");
    return Future.succeededFuture(cat);
  }

  @Override
  public Future<Cat> add(Cat cat) {
    return Future.succeededFuture(cat);
  }
}
