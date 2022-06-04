package com.example.ankieter.model;

import java.util.List;

public class FormInput {
  public String title;
  public String description;
  public Boolean locked;
  public String password;
  public Boolean answersLocked;
  public List<QuestionInput> questions;

  public boolean valid() {
    if (this.title == null || this.title.length() == 0) {
      System.out.println("Invalid title");
      return false;
    }
    if (this.questions == null || this.questions.toArray().length == 0) {
      System.out.println("Invalid questions");
      return false;
    }
    return true;
  }

  public Form getForm(String userId) {
    Form form = new Form();
    form.setTitle(title);
    form.setDescription(description);
    form.setLocked(locked);
    form.setAnswersLocked(answersLocked);
    form.setPassword(password);
    form.setUserId(userId);

    return form;
  }

  public List<Question> getQuestions(String formId) {
    List<Question> questions = this.questions.stream().map(question -> question.getQuestion(formId)).toList();
    return questions;
  }

}
