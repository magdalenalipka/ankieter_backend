package com.example.ankieter.model;

public class QuestionInput {

  public String type;
  public String title;
  public String description;
  public boolean required;
  public String[] answers;

  public boolean valid() {
    if (!this.type.equals("singleChoice") && !this.type.equals("multiChoice")) {
      System.out.println("Invalid question type");
      return false;
    }
    if (this.title == null || this.title.length() == 0) {
      System.out.println("Invalid question title");
      return false;
    }
    if (this.answers == null || this.answers.length == 0) {
      System.out.println("Invalid question answers");
      return false;
    }
    if (this.answers.length > 10) {
      return false;
    }

    return true;
  }

  public Question getQuestion(String formId) {
    Question question;
    if (this.type.equals("singleChoice")) {
      question = new SingleChoiceQuestion();
    } else if (this.type.equals("multiChoice")) {
      question = new MultiChoiceQuestion();
    } else {
      return null;
    }

    question.setDescription(description);
    question.setTitle(title);
    question.setAnswers(answers);
    question.setFormId(formId);
    question.setRequired(required);

    return question;
  }

}
