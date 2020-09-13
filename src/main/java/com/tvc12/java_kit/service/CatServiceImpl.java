package com.tvc12.java_kit.service;

import com.google.inject.Inject;
import com.tvc12.java_kit.dao.CatDAO;
import com.tvc12.java_kit.domain.exception.NotFoundException;
import com.tvc12.java_kit.domain.model.Cat;
import io.vertx.core.Future;

import java.util.Optional;

public class CatServiceImpl implements CatService {
  @Inject
  private CatDAO catDAO;

  @Override
  public Future<Cat> get(String id) {
    final Optional<Cat> maybeCat = catDAO.findById(id);
    if (maybeCat.isPresent()) {
      return Future.succeededFuture(maybeCat.get());
    } else {
      return Future.failedFuture(new NotFoundException());
    }
  }

  @Override
  public Future<Cat> add(Cat cat) {
    catDAO.add(cat);
    return Future.succeededFuture(cat);
  }
}
