package com.tvc12.java_kit.dao;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;
import com.tvc12.java_kit.domain.model.Cat;

import javax.persistence.EntityManager;
import java.util.Optional;

@Transactional
public class CatDAO extends BaseDAO<Cat> {

  @Inject()
  public CatDAO(Provider<EntityManager> entityManager) {
    super(entityManager);
  }

  public Optional<Cat> findById(final String id) {
    return findById(Cat.class, id);
  }
}
