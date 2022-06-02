package com.example.ankieter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "forms")
public class Form extends AuditModel {
  @Id
  @GeneratedValue(generator = "form_generator")
  @SequenceGenerator(name = "form_generator", sequenceName = "form_generator", initialValue = 1000)
  private Long id;

  @NotBlank
  @Size(min = 3, max = 100)
  private String title;

  @Column(columnDefinition = "text")
  private String description;

  @Column(columnDefinition = "boolean")
  private Boolean locked;

  // if password is defined then form is password protected
  @Column(columnDefinition = "text")
  private String password;

  @Column(columnDefinition = "boolean", name = "answers_locked")
  private Boolean answersLocked;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_id", nullable = false)
  @Cascade(org.hibernate.annotations.CascadeType.ALL)
  @JsonIgnore
  private User user;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

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

}