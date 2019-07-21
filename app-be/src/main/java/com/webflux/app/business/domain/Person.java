package com.webflux.app.business.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Person {

  @Id
  @GeneratedValue
  private Long id;
  private String nickname;
  private String email;

  public Person(Long id, String nickname, String email) {
    this.id = id;
    this.nickname = nickname;
    this.email = email;
  }

  public Person() {}

  public Long getId() {
    return id;
  }

  public String getNickname() {
    return nickname;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
