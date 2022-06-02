package com.example.ankieter.model;

public class QuestionInput {

  private String type;
  private String title;
  private String description;
  private String[] answers;

  public Question getQuestion(String formId) {
    Question question;
    if (this.type == "singleChoice") {
      question = new SingleChoiceQuestion();
    } else if (this.type == "multiChoice") {
      question = new MultiChoiceQuestion();
    } else {
      return null;
    }

    question.setDescription(description);
    question.setTitle(title);
    question.setAnswers(answers);
    question.setFormId(formId);

    return question;
  }

}
