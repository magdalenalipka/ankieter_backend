package com.example.ankieter.model;

import java.util.Arrays;

import com.example.ankieter.repository.QuestionRepository;

public class AnswerInput extends AuditModel {
  public String questionId;
  public String choiceIndex;
  public String[] choiceIndices;

  public boolean valid(String formId, QuestionRepository questionRepository) {
    if (this.choiceIndex != null && this.choiceIndices != null) {
      System.out.println("double choice");
      return false;
    }

    Question question = questionRepository.getById(this.questionId);
    if (!question.getFormId().equals(formId)) {
      System.out.println("wrong question id");
      return false;
    }

    if (question.getType().equals("singleChoice")
        && (this.choiceIndex == null || Integer.parseInt(this.choiceIndex) >= question.getAnswers().length)) {
      System.out.println("missing single");
      return false;
    }
    if (question.getType().equals("multiChoice")) {
      if (this.choiceIndices == null) {
        System.out.println("missing multi");
        return false;
      }
      for (String choice : this.choiceIndices) {
        if (Integer.parseInt(choice) >= question.getAnswers().length) {
          System.out.println("too big index");
          return false;
        }
      }
    }

    return true;
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
