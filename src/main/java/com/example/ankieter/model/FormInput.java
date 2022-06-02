package com.example.ankieter.model;

import java.util.List;

public class FormInput {
  private String title;
  private String description;
  private Boolean locked;
  private String password;
  private Boolean answersLocked;
  private List<QuestionInput> questions;

  Form getForm(String userId) {
    Form form = new Form();
    form.setTitle(title);
    form.setDescription(description);
    form.setLocked(locked);
    form.setAnswersLocked(answersLocked);
    form.setPassword(password);
    form.setUserId(userId);

    return form;
  }

  List<Question> getQuestions(String formId) {
    List<Question> questions = this.questions.stream().map(question -> question.getQuestion(formId)).toList();
    return questions;
  }

}
