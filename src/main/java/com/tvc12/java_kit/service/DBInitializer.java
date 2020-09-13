package com.tvc12.java_kit.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.persist.PersistService;

@Singleton
public class DBInitializer {
  @Inject
  public DBInitializer(PersistService service) {
    System.out.println("DBInitializer::");
    service.start();
  }
}
