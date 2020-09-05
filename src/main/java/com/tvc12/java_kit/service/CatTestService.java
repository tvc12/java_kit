package com.tvc12.java_kit.service;

import com.tvc12.java_kit.domain.exception.AppException;
import com.tvc12.java_kit.domain.model.Cat;

public class CatTestService implements CatService {

  @Override
  public Cat get(String id) throws AppException {
    return new Cat(id, "Kitty", 12, "Mety");
  }

  @Override
  public Cat add(Cat cat) throws AppException {
    return new Cat("12", "Doggy", 1, "Husky");
  }
}
