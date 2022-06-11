package com.example.ankieter.model;

import static java.util.stream.Collectors.toList;
import java.util.List;

public class FormInput {
  public String title;
  public String description;
  public Boolean locked;
  public String password;
  public Boolean answersLocked;
  public List<QuestionInput> questions;

  public String invalid() {
    if (this.title == null || this.title.length() == 0) {
      return "Ankieta powinna mieć tytuł.";
    }
    if (this.questions == null || this.questions.toArray().length == 0) {
      return "Ankieta powinna mieć co najmniej jedno pytanie.";
    }
    if (this.questions.toArray().length > 100) {
      return "Ankieta nie może mieć więcej niż 100 pytań.";
    }

    return null;
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
    List<Question> questions = this.questions.stream().map(question -> question.getQuestion(formId)).collect(toList());
    return questions;
  }

}
