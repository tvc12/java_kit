package com.tvc12.java_kit.service;

import com.google.inject.Inject;
import com.tvc12.java_kit.domain.exception.AppException;
import com.tvc12.java_kit.domain.exception.InternalErrorException;
import com.tvc12.java_kit.domain.exception.NotFoundException;
import com.tvc12.java_kit.domain.model.Cat;

public class CatCachedService implements CatService {
  @Inject
  private CacheService<String, Cat> cache;

  @Override
  public Cat get(String id) throws AppException {
    var cat = cache.get(id);
    if (cat != null) {
      return cat;
    } else {
      throw new NotFoundException();
    }
  }

  @Override
  public Cat add(Cat cat) throws AppException {
    var success = cache.put(cat.id, cat);
    if (success) {
      return cat;
    } else {
      throw new InternalErrorException();
    }
  }
}
