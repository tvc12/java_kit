package com.tvc12.java_kit;

import com.google.inject.Module;
import com.tvc12.java_kit.controller.AuthenController;
import com.tvc12.java_kit.controller.CatController;
import com.tvc12.java_kit.module.AbstractApp;
import com.tvc12.java_kit.module.MainModule;
import io.vertx.ext.web.Router;

public class MainVerticle extends AbstractApp {
  @Override
  protected Module[] modules() {
    return new Module[]{
      new MainModule(),
    };
  }

  @Override
  protected void setupRouter(Router router) {
    injector.getInstance(CatController.class).configure(router);
    injector.getInstance(AuthenController.class).configure(router);
  }
}
