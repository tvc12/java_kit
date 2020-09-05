package com.tvc12.java_kit.domain.model;

import org.codehaus.jackson.annotate.JsonProperty;

public class Cat {
  public String id;
  public String name;
  public int age;

  @JsonProperty("pet_category")
  public String petCategory;

  public Cat(String id, String name, int age, String petCategory) {
    this.id = id;
    this.name = name;
    this.age = age;
    this.petCategory = petCategory;
  }

}
