package com.tvc12.java_kit.module;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.ScopeAnnotation;
import com.google.inject.Singleton;
import com.tvc12.java_kit.domain.model.Cat;
import com.tvc12.java_kit.service.*;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.authentication.AuthenticationProvider;
import io.vertx.ext.auth.shiro.PropertiesProviderConstants;
import io.vertx.ext.auth.shiro.ShiroAuth;
import io.vertx.ext.auth.shiro.ShiroAuthOptions;
import io.vertx.ext.auth.shiro.ShiroAuthRealmType;

public class MainModule extends AbstractModule {
  @Override
  protected void configure() {
    // #1
    bind(CatService.class).to(CatServiceImpl.class).asEagerSingleton();
    bind(AuthenService.class).to(AuthenServiceImpl.class).asEagerSingleton();
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
    JsonObject config = new JsonObject().put(PropertiesProviderConstants.PROPERTIES_PROPS_PATH_FIELD, "classpath:test-auth.properties");
    final ShiroAuthOptions options = new ShiroAuthOptions().setType(ShiroAuthRealmType.PROPERTIES).setConfig(config);
    return ShiroAuth.create(vertx, options);
  }
}
