package com.tvc12.java_kit.domain.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users")
public class User implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column
  public int id;

  @Column
  public String name;

  @Column
  public String email;

  public User(int id, String name, String email) {
    this.id = id;
    this.name = name;
    this.email = email;
  }

  public User() {

  }
}
