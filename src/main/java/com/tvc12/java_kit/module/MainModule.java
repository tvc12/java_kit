package com.tvc12.java_kit.module;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.tvc12.java_kit.domain.model.Cat;
import com.tvc12.java_kit.service.CacheService;
import com.tvc12.java_kit.service.CatService;
import com.tvc12.java_kit.service.CatServiceImpl;
import com.tvc12.java_kit.service.InMemoryCacheService;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.authentication.AuthenticationProvider;
import io.vertx.ext.auth.shiro.ShiroAuth;
import io.vertx.ext.auth.shiro.ShiroAuthOptions;
import io.vertx.ext.auth.shiro.ShiroAuthRealmType;

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


  @Provides
  @Singleton
  AuthenticationProvider providesAuthenProvider(Vertx vertx) {
    JsonObject config = new JsonObject().put("properties_path", "classpath:test-auth.properties");
    return ShiroAuth.create(vertx, new ShiroAuthOptions()
      .setType(ShiroAuthRealmType.PROPERTIES)
      .setConfig(config));
  }
}
