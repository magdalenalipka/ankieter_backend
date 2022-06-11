package com.example.ankieter.model;

public class QuestionInput {

  public String type;
  public String title;
  public String description;
  public boolean required;
  public String[] answers;

  public String invalid() {
    if (!this.type.equals("singleChoice") && !this.type.equals("multiChoice")) {
      return "Nieobsługiwany typ pytania.";
    }
    if (this.title == null || this.title.length() == 0) {
      return "Pytanie powinno mieć tytuł.";
    }
    if (this.answers == null || this.answers.length == 0) {
      return "Pytanie powinno mieć co najmniej jedną odpowiedź.";
    }
    if (this.answers.length > 10) {
      return "Pytanie nie może mieć więcej niż 10 odpowiedzi.";
    }

    return null;
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
