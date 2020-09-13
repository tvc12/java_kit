package com.tvc12.java_kit.module;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.tvc12.java_kit.service.DBInitializer;

import java.util.Properties;

public class HibernateModule extends AbstractModule {
  @Override
  protected void configure() {
    install(jpaModule());
    bind(DBInitializer.class).asEagerSingleton();
  }

  private Module jpaModule() {
    final Properties properties = new Properties();
    properties.put("hibernate.connection.driver_class", "org.postgresql.Driver");
    properties.put("hibernate.archive.autodetection", "class");
    properties.put("hibernate.show_sql", "true");
    properties.put("hibernate.format_sql", "true");
    properties.put("hibernate.hbm2ddl.auto", "update");
    String url = System.getenv("DB_CONNECTION_URL");
    String username = System.getenv("DB_USERNAME");
    String password = System.getenv("DB_PASSWORD");

    url = url != null ? url : "jdbc:postgresql://localhost:5432/java_kit_db";
    username = username != null ? username : "java_kit";
    password = password != null ? password : "java_kit";

    properties.put("hibernate.connection.url", url);
    properties.put("hibernate.connection.username", username);
    properties.put("hibernate.connection.password", password);

    JpaPersistModule jpaModule = new JpaPersistModule("java_kit");
    jpaModule.properties(properties);

    return jpaModule;
  }
}
