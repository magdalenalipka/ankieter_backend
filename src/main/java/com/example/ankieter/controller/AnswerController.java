package com.example.ankieter.controller;

import com.example.ankieter.model.Answer;
import com.example.ankieter.model.AnswerInput;
import com.example.ankieter.model.AnswerSetInput;
import com.example.ankieter.model.Form;
import com.example.ankieter.model.User;
import com.example.ankieter.repository.AnswerRepository;
import com.example.ankieter.repository.FormRepository;
import com.example.ankieter.repository.QuestionRepository;
import com.example.ankieter.repository.UserRepository;
import com.example.ankieter.utilities.Headers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import static java.util.stream.Collectors.groupingBy;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(methods = { RequestMethod.DELETE, RequestMethod.GET, RequestMethod.OPTIONS,
    RequestMethod.POST, RequestMethod.PATCH }, allowedHeaders = "*")
@RestController
public class AnswerController {

  @Autowired
  QuestionRepository questionRepository;
  @Autowired
  AnswerRepository answerRepository;
  @Autowired
  FormRepository formRepository;
  @Autowired
  UserRepository userRepository;

  @RequestMapping(value = "/forms/{form_id}/answers", method = RequestMethod.OPTIONS)
  public ResponseEntity addFormOptions(@RequestHeader("Authorization") String auth,
      @RequestHeader("Origin") String origin, @PathVariable("form_id") String formId) {
    return ResponseEntity.ok().headers(new Headers(origin)).build();
  }

  @PostMapping(value = "/forms/{form_id}/answers", consumes = "application/json")
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

    for (Answer answer : answers) {
      answerRepository.save(answer);
    }

    return ResponseEntity.ok().headers(new Headers(origin)).build();
  }

  @GetMapping("/forms/{form_id}/answers")
  public ResponseEntity getFormAnswers(@PathVariable(name = "form_id") String formId,
      @RequestHeader("Origin") String origin,
      @RequestHeader("Authorization") String auth) {

    Form form = formRepository.getById(formId);

    if (form.getAnswersLocked()) {
      User user = userRepository.getUserFromAuth(auth);
      if (user == null || !user.getId().equals(form.getUserId())) {
        return ResponseEntity.status(403).headers(new Headers(origin)).build();
      }
    }

    List<Answer> answers = new ArrayList(
        answerRepository.getByFormId(formId).stream().collect(groupingBy(Answer::getAnswerSetId)).values());

    return ResponseEntity.ok().headers(new Headers(origin)).body(answers);
  }

}
