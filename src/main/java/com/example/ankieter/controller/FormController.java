package com.example.ankieter.controller;

import com.example.ankieter.model.Form;
import com.example.ankieter.model.FormInput;
import com.example.ankieter.model.User;
import com.example.ankieter.repository.FormRepository;
import com.example.ankieter.repository.UserRepository;
import com.example.ankieter.utilities.Headers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@CrossOrigin(methods = { RequestMethod.DELETE, RequestMethod.GET, RequestMethod.OPTIONS,
    RequestMethod.POST }, allowedHeaders = "*")
@RestController
public class FormController {

  @Autowired
  private FormRepository formRepository;

  @Autowired
  private UserRepository userRepository;

  @PostMapping("/forms")
  public ResponseEntity addForm(@RequestHeader("Authorization") String auth, @RequestHeader("Origin") String origin,
      @RequestBody FormInput formInput) {

    User user = userRepository.getUserFromAuth(auth);

    if (user == null) {
      return ResponseEntity.status(403).headers(new Headers(origin)).build();
    }

    // System.out.println();

    // TODO: implement
    // vallidate
    // save
    // map over questions
    // - save
    // - map over answers
    // -- save

    return ResponseEntity.ok().headers(new Headers(origin)).build();
  }

  @GetMapping("/forms")
  public ResponseEntity getPublicForms(@RequestHeader("Origin") String origin) {

    List<Form> forms = formRepository.getAllPublicForms();

    System.out.println(forms.toArray().length);

    return ResponseEntity.ok().headers(new Headers(origin)).body(forms);
  }

  @DeleteMapping("/forms/{form_id}")
  public ResponseEntity<?> deleteForm(@RequestHeader("Authorization") String auth,
      @RequestHeader("Origin") String origin, @PathVariable("form_id") String formId) {

    User user = userRepository.getUserFromAuth(auth);

    if (user == null) {
      return ResponseEntity.status(403).headers(new Headers(origin)).build();
    }

    Form form = formRepository.getById(formId);
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
