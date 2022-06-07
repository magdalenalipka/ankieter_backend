package com.example.ankieter.controller;

import com.example.ankieter.model.Form;
import com.example.ankieter.model.FormInput;
import com.example.ankieter.model.FormResponse;
import com.example.ankieter.model.FormUpdateInput;
import com.example.ankieter.model.Question;
import com.example.ankieter.model.QuestionInput;
import com.example.ankieter.model.User;
import com.example.ankieter.repository.FormRepository;
import com.example.ankieter.repository.QuestionRepository;
import com.example.ankieter.repository.UserRepository;
import com.example.ankieter.utilities.Headers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@CrossOrigin(methods = { RequestMethod.DELETE, RequestMethod.GET, RequestMethod.OPTIONS,
    RequestMethod.POST, RequestMethod.PATCH }, allowedHeaders = "*")
@RestController
public class FormController {

  @Autowired
  private FormRepository formRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  QuestionRepository questionRepository;

  @RequestMapping(value = "/forms", method = RequestMethod.OPTIONS)
  public ResponseEntity addFormOptions(@RequestHeader("Authorization") String auth,
      @RequestHeader("Origin") String origin) {
    return ResponseEntity.ok().headers(new Headers(origin)).build();
  }

  @PostMapping(value = "/forms", consumes = "application/json")
  public ResponseEntity addForm(@RequestHeader("Authorization") String auth, @RequestHeader("Origin") String origin,
      @RequestBody FormInput formInput) {

    User user = userRepository.getUserFromAuth(auth);

    if (user == null) {
      return ResponseEntity.status(403).headers(new Headers(origin)).build();
    }

    if (!formInput.valid()) {
      return ResponseEntity.status(400).headers(new Headers(origin)).build();
    }

    for (QuestionInput question : formInput.questions) {
      if (!question.valid()) {
        return ResponseEntity.status(400).headers(new Headers(origin)).build();

      }
    }

    Form form = formInput.getForm(user.getId());
    formRepository.save(form);

    for (Question question : formInput.getQuestions(form.getId())) {
      questionRepository.save(question);
    }

    return ResponseEntity.ok().headers(new Headers(origin)).build();
  }

  @GetMapping("/forms")
  public ResponseEntity getPublicForms(@RequestHeader("Origin") String origin) {

    List<Form> forms = formRepository.getAllPublicForms();

    return ResponseEntity.ok().headers(new Headers(origin)).body(forms);
  }

  @RequestMapping(value = "/forms/{form_id}", method = RequestMethod.OPTIONS)
  public ResponseEntity getFormOptions(@RequestHeader("Origin") String origin, @PathVariable("form_id") String formId) {
    return ResponseEntity.ok().headers(new Headers(origin)).build();
  }

  @GetMapping("/forms/{form_id}")
  public ResponseEntity getForm(@RequestHeader("Origin") String origin, @PathVariable("form_id") String formId) {

    Form form = formRepository.findById(formId).orElse(null);

    if (form == null) {
      return ResponseEntity.status(404).headers(new Headers(origin)).build();
    }

    List<Question> questions = Stream
        .concat(questionRepository.getFormQuestions(formId, "singleChoice").stream(),
            questionRepository.getFormQuestions(formId, "multiChoice").stream())
        .collect(Collectors.toList());

    FormResponse response = new FormResponse();
    response.detail = form;
    response.questions = questions;

    return ResponseEntity.ok().headers(new Headers(origin)).body(response);
  }

  @PatchMapping(value = "/forms/{form_id}", consumes = "application/json")
  public ResponseEntity updateForm(@RequestHeader("Authorization") String auth,
      @RequestHeader("Origin") String origin, @PathVariable("form_id") String formId,
      @RequestBody FormUpdateInput formInput) {
    User user = userRepository.getUserFromAuth(auth);

    if (user == null) {
      return ResponseEntity.status(403).headers(new Headers(origin)).build();
    }

    Form form = formRepository.getById(formId);
    if (!form.getUserId().equals(user.getId())) {
      return ResponseEntity.status(403).headers(new Headers(origin)).build();
    }

    if (formInput.answersLocked != null) {
      form.setAnswersLocked(formInput.answersLocked);
    }
    if (formInput.locked != null) {
      form.setLocked(formInput.locked);
    }
    if (formInput.description != null) {
      form.setDescription(formInput.description.length() != 0 ? formInput.description : null);
    }
    if (formInput.password != null) {
      form.setPassword(formInput.password.length() != 0 ? formInput.password : null);
    }

    formRepository.save(form);

    return ResponseEntity.status(200).headers(new Headers(origin)).build();
  }

  @DeleteMapping("/forms/{form_id}")
  public ResponseEntity<?> deleteForm(@RequestHeader("Authorization") String auth,
      @RequestHeader("Origin") String origin, @PathVariable("form_id") String formId) {

    User user = userRepository.getUserFromAuth(auth);

    if (user == null) {
      return ResponseEntity.status(403).headers(new Headers(origin)).build();
    }

    Form form = formRepository.getById(formId);

    if (!form.getUserId().equals(user.getId())) {
      return ResponseEntity.status(403).headers(new Headers(origin)).build();
    }

    formRepository.delete(form);

    return ResponseEntity.status(204).headers(new Headers(origin)).build();
  }

  @RequestMapping(value = "/users/forms", method = RequestMethod.OPTIONS)
  public ResponseEntity getUserFormsOptions(@RequestHeader("Authorization") String auth,
      @RequestHeader("Origin") String origin) {
    return ResponseEntity.ok().headers(new Headers(origin)).build();
  }

  @GetMapping("/users/forms")
  public ResponseEntity getUserForms(@RequestHeader("Authorization") String auth,
      @RequestHeader("Origin") String origin) {

    User user = userRepository.getUserFromAuth(auth);

    if (user == null) {
      return ResponseEntity.status(403).headers(new Headers(origin)).build();
    }

    List<Form> forms = formRepository.getUserForms(user.getId());

    return ResponseEntity.ok().headers(new Headers(origin)).body(forms);
  }
}
