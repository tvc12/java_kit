package com.tvc12.java_kit.module;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.tvc12.java_kit.domain.model.Cat;
import com.tvc12.java_kit.service.CacheService;
import com.tvc12.java_kit.service.CatCachedService;
import com.tvc12.java_kit.service.CatService;
import com.tvc12.java_kit.service.InMemoryCacheService;

public class MainModule extends AbstractModule {
  @Override
  protected void configure() {
    // #1
    bind(CatService.class).to(CatCachedService.class).asEagerSingleton();
  }

  // # 2
  @Provides
  @Singleton
  CacheService<String, Cat> providesCacheService() {
    return new InMemoryCacheService<String, Cat>();
  }
}
