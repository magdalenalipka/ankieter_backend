package com.example.ankieter.model;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.ankieter.repository.QuestionRepository;

public class AnswerInput extends AuditModel {
  public String questionId;
  public String choiceIndex;
  public String[] choiceIndices;

  @Autowired
  QuestionRepository questionRepository;

  public boolean valid(String formId) {
    if (this.choiceIndex != null && this.choiceIndices != null) {
      return false;
    }

    Question question = questionRepository.getById(this.questionId);
    if (!question.getFormId().equals(formId)) {
      return false;
    }

    if (question.type.equals("singleChoice")
        && (this.choiceIndex == null || Integer.parseInt(this.choiceIndex) >= question.getAnswers().length)) {
      return false;
    }
    if (question.type.equals("multiChoice")) {
      if (this.choiceIndices == null) {
        return false;
      }
      for (String choice : this.choiceIndices) {
        if (Integer.parseInt(choice) >= question.getAnswers().length) {
          return false;
        }
      }
    }

    return true;
  }

  public Answer getAnswer(String answerSetId) {
    Question question = questionRepository.getById(this.questionId);

    Answer answer;
    if (question.getType().equals("singleChoice")) {
      answer = new SingleChoiceAnswer();
      ((SingleChoiceAnswer) answer).setChoice(Integer.parseInt(this.choiceIndex));
    } else if (question.getType().equals("multiChoice")) {
      answer = new MultiChoiceAnswer();
      ((MultiChoiceAnswer) answer)
          .setChoice(Arrays.asList(this.choiceIndices).stream().mapToInt(Integer::parseInt).toArray());
    } else {
      return null;
    }

    answer.setAnswerSetId(this.getId());

    return answer;
  }

}
