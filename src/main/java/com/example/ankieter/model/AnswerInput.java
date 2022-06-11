package com.example.ankieter.model;

import java.util.Arrays;

import com.example.ankieter.repository.QuestionRepository;

public class AnswerInput extends AuditModel {
  public String questionId;
  public String choiceIndex;
  public String[] choiceIndices;

  public String invalid(String formId, QuestionRepository questionRepository) {
    if (this.choiceIndex != null && this.choiceIndices != null) {
      return "Brak odpowiedzi na pytanie.";
    }

    Question question = questionRepository.getById(this.questionId);
    if (!question.getFormId().equals(formId)) {
      return "Pytanie nie należy do podanej ankiety.";
    }

    if (question.getType().equals("singleChoice")
        && (this.choiceIndex == null || Integer.parseInt(this.choiceIndex) >= question.getAnswers().length)) {
      return "Brak odpowiedzi na pytanie";
    }
    if (question.getType().equals("multiChoice")) {
      if (this.choiceIndices == null) {
        return "Brak odpowiedzi na pytanie";
      }
      for (String choice : this.choiceIndices) {
        if (Integer.parseInt(choice) >= question.getAnswers().length) {
          return "Podana odpowiedź nie istnieje.";
        }
      }
    }

    return null;
  }

  public Answer getAnswer(String answerSetId, QuestionRepository questionRepository) {
    Question question = questionRepository.getById(this.questionId);

    Answer answer;
    if (question.getType().equals("singleChoice")) {
      answer = new SingleChoiceAnswer();
      ((SingleChoiceAnswer) answer).setChoice(Integer.parseInt(this.choiceIndex));
    } else if (question.getType().equals("multiChoice")) {
      answer = new MultiChoiceAnswer();
      int[] choice = Arrays.asList(this.choiceIndices).stream().mapToInt(Integer::parseInt).toArray();
      ((MultiChoiceAnswer) answer)
          .setChoice(choice);
    } else {
      return null;
    }

    answer.setAnswerSetId(this.getId());
    answer.setQuestionId(questionId);

    return answer;
  }

}
