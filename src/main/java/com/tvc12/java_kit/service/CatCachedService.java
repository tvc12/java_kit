package com.tvc12.java_kit.service;

import com.google.inject.Inject;
import com.tvc12.java_kit.domain.exception.AppException;
import com.tvc12.java_kit.domain.exception.InternalErrorException;
import com.tvc12.java_kit.domain.exception.NotFoundException;
import com.tvc12.java_kit.domain.model.Cat;
import io.vertx.core.Future;
import io.vertx.core.Promise;

public class CatCachedService implements CatService {
  @Inject
  private CacheService<String, Cat> cache;

  @Override
  public Future<Cat> get(String id) throws AppException {
    var cat = cache.get(id);
    if (cat != null) {
      return Future.succeededFuture(cat);
    } else {
      return Future.failedFuture(new NotFoundException());
    }
  }

  @Override
  public Future<Cat> add(Cat cat) throws AppException {
    var success = cache.put(cat.id, cat);
    if (success) {
      return Future.succeededFuture(cat);
    } else {
      return Future.failedFuture(new InternalErrorException());
    }
  }
}
