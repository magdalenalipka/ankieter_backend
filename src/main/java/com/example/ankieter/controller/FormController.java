package com.example.ankieter.controller;

import com.example.ankieter.model.Form;
import com.example.ankieter.model.FormInput;
import com.example.ankieter.model.FormUpdateInput;
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

  @PostMapping("/forms")
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

    formInput.getQuestions(form.getId()).stream().map(question -> questionRepository.save(question));

    return ResponseEntity.ok().headers(new Headers(origin)).build();
  }

  @GetMapping("/forms")
  public ResponseEntity getPublicForms(@RequestHeader("Origin") String origin) {

    List<Form> forms = formRepository.getAllPublicForms();

    return ResponseEntity.ok().headers(new Headers(origin)).body(forms);
  }

  @PatchMapping("/forms/{form_id}")
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
