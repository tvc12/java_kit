package com.tvc12.java_kit.module;

import com.google.inject.AbstractModule;
import com.google.inject.Provider;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.tvc12.java_kit.dao.CatDAO;
import com.tvc12.java_kit.domain.model.Cat;
import com.tvc12.java_kit.service.*;

import javax.persistence.EntityManager;

public class MainModule extends AbstractModule {
  @Override
  protected void configure() {
    // #1
    bind(CatService.class).to(CatServiceImpl.class).asEagerSingleton();
  }

  // # 2
  @Provides
  @Singleton
  CacheService<String, Cat> providesCacheService() {
    return new InMemoryCacheService<String, Cat>();
  }
}
