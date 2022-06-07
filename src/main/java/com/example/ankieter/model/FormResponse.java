package com.example.ankieter.model;

import java.util.List;

public class FormResponse extends AuditModel {
  public Form detail;
  public List<Question> questions;

  public Form getDetail() {
    return this.detail;
  }

  public List<Question> getQuestions() {
    return this.questions;
  }
}
