package com.tvc12.java_kit.module;

import com.google.inject.AbstractModule;
import com.tvc12.java_kit.service.CatService;
import com.tvc12.java_kit.service.CatTestService;

public class TestModule extends AbstractModule {
  @Override
  protected void configure() {
    bind(CatService.class).to(CatTestService.class).asEagerSingleton();
  }
}
