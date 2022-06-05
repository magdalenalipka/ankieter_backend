package com.example.ankieter.controller;

import com.example.ankieter.model.Answer;
import com.example.ankieter.model.AnswerInput;
import com.example.ankieter.model.AnswerSetInput;
import com.example.ankieter.repository.AnswerRepository;
import com.example.ankieter.repository.QuestionRepository;
import com.example.ankieter.utilities.Headers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@CrossOrigin(methods = { RequestMethod.DELETE, RequestMethod.GET, RequestMethod.OPTIONS,
    RequestMethod.POST, RequestMethod.PATCH }, allowedHeaders = "*")
@RestController
public class AnswerController {

  @Autowired
  QuestionRepository questionRepository;
  @Autowired
  AnswerRepository answerRepository;

  @PostMapping("/forms/{form_id}/answers")
  public ResponseEntity addForm(@PathVariable(name = "form_id") String formId, @RequestHeader("Origin") String origin,
      @RequestBody AnswerSetInput answerSetInput, @RequestParam(name = "password") String password) {

    if (!answerSetInput.authorized(formId, password)) {
      return ResponseEntity.status(403).headers(new Headers(origin)).build();
    }
    if (!answerSetInput.valid(formId)) {
      return ResponseEntity.status(400).headers(new Headers(origin)).build();
    }

    for (AnswerInput answerInput : answerSetInput.answers) {
      if (!answerInput.valid(formId)) {
        return ResponseEntity.status(400).headers(new Headers(origin)).build();
      }
    }

    List<Answer> answers = answerSetInput.getAnswers();
    answers.stream().map(answer -> answerRepository.save(answer));

    return ResponseEntity.ok().headers(new Headers(origin)).build();
  }

  @GetMapping("/forms/{form_id}/answers")
  public ResponseEntity getFormAnswers(@RequestHeader("Origin") String origin,
      @RequestHeader("Authorization") String auth) {

    // TODO

    return ResponseEntity.ok().headers(new Headers(origin)).build();
  }

}
