package com.tvc12.java_kit.module;

import com.google.inject.AbstractModule;
import io.vertx.core.Vertx;

public class VertxModule extends AbstractModule {
  private final Vertx vertx;


  public VertxModule(Vertx vertx) {
    this.vertx = vertx;
  }


  @Override
  protected void configure() {
    bind(Vertx.class).toInstance(this.vertx);
  }
}
