package com.tvc12.java_kit.domain.model;

import jdk.nashorn.internal.ir.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Immutable
@Table(name = "cats")
public class Cat implements Serializable {

  @Id
  @Column
  public String id;

  @NotNull
  @Size(min = 4, max = 250)
  @Column
  public String name = "";

  @NotNull
  @Min(0)
  @Column
  public int age;

  @Column
  public String petCategory = "";

  public Cat() {

  }

  public Cat(String id, String name, int age, String petCategory) {
    this.id = id;
    this.name = name;
    this.age = age;
    this.petCategory = petCategory;
  }

}
