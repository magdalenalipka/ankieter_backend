package com.example.ankieter.model;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.ankieter.repository.FormRepository;
import com.example.ankieter.repository.QuestionRepository;

public class AnswerSetInput {
  public AnswerInput[] answers;
  public String id;

  AnswerSetInput() {
    this.id = UUID.randomUUID().toString();
  }

  @Autowired
  FormRepository formRepository;
  @Autowired
  QuestionRepository questionRepository;

  public boolean authorized(String formId, String password) {
    Form form = formRepository.getById(formId);

    if (form.isPasswordProtected() && !form.checkPassword(password)) {
      return false;
    }

    return true;
  }

  public boolean valid(String formId) {

    Form form = formRepository.getById(formId);

    if (form == null) {
      return false;
    }
    if (form.getLocked()) {
      return false;
    }

    List<Question> requiredQuestions = Stream
        .concat(questionRepository.getFormRequiredQuestions(formId, "singleChoice").stream(),
            questionRepository.getFormRequiredQuestions(formId, "multiChoice").stream())
        .collect(Collectors.toList());

    for (Question requiredQuestion : requiredQuestions) {
      if (Arrays.stream(this.answers).filter(answer -> answer.questionId.equals(requiredQuestion.getId()))
          .collect(toList()).toArray().length == 0) {
        return false;
      }
    }

    return true;
  }

  public List<Answer> getAnswers() {
    return Arrays.asList(this.answers).stream().map(answerInput -> answerInput.getAnswer(this.id)).collect(toList());
  }

}
