package com.tvc12.java_kit.domain.model;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "accounts")
public class Account implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column
  public int id;

  @Column(unique = true)
  @Size(min = 5, max = 50)
  public String username;

  @Column
  public String password;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "users")
  public User user;

  public Account(int id, @Size(min = 5, max = 50) String username, String password, User user) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.user = user;
  }

  public Account() {

  }
}
