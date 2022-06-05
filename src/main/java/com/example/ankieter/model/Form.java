package com.example.ankieter.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "forms")
public class Form extends AuditModel {

  @NotBlank
  @Size(min = 3, max = 100)
  private String title;

  @Column(columnDefinition = "text")
  private String description;

  @Column(columnDefinition = "boolean")
  private Boolean locked;

  // if password is defined then form is password protected
  @Column(columnDefinition = "text")
  private String password = null;

  @Column(columnDefinition = "boolean", name = "answers_locked")
  private Boolean answersLocked;

  @Column(columnDefinition = "text", name = "user_id")
  private String userId;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Boolean getLocked() {
    return this.locked;
  }

  public void setLocked(Boolean locked) {
    this.locked = locked;
  }

  public Boolean getAnswersLocked() {
    return this.answersLocked;
  }

  public void setAnswersLocked(Boolean answersLocked) {
    this.answersLocked = answersLocked;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public boolean isPasswordProtected() {
    return this.password != null;
  }

  public boolean checkPassword(String password) {
    return this.password.equals(password);
  }

  public String getUserId() {
    return this.userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

}