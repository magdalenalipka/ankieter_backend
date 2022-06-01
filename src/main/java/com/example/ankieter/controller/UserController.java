package com.example.ankieter.controller;

import com.example.ankieter.exception.ConflictException;
import com.example.ankieter.exception.NotFoundException;
import com.example.ankieter.exception.UnauthorizedException;
import com.example.ankieter.model.Form;
import com.example.ankieter.model.User;
import com.example.ankieter.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

  @Autowired
  private UserRepository userRepository;

  @PostMapping("/users")
  public ResponseEntity addUser(@RequestHeader("Authorization") String auth) {

    String base64Credentials = auth.substring("Basic".length()).trim();
    byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
    String credentials = new String(credDecoded, StandardCharsets.UTF_8);
    // credentials = username:password
    String[] values = credentials.split(":", 2);

    Optional<User> conflictUser = userRepository.findById(values[0]);
    if (conflictUser.isPresent()) {
      return ResponseEntity.badRequest().body("This nick is taken.");
    }

    User newUser = new User();
    newUser.setNick(values[0]);
    newUser.setPassword(values[1]);

    User savedUser = userRepository.save(newUser);

    return ResponseEntity.ok(savedUser);
  }

  @RequestMapping(value = "/users", method = RequestMethod.OPTIONS)
  public ResponseEntity checkUser(@RequestHeader("Authorization") String auth) {
    String base64Credentials = auth.substring("Basic".length()).trim();
    byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
    String credentials = new String(credDecoded, StandardCharsets.UTF_8);
    // credentials = username:password
    final String[] values = credentials.split(":", 2);

    Optional<User> user = userRepository.findById(values[0]);

    if (!user.isPresent()) {
      throw new NotFoundException("User with nick " + values[0] + " does not exist");
    }

    if (!user.get().checkPassword(values[1])) {
      throw new UnauthorizedException("Incorrect password");
    }

    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/users")
  public ResponseEntity<?> deleteAnswer(@RequestHeader("Authorization") String auth) {

    String base64Credentials = auth.substring("Basic".length()).trim();
    byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
    String credentials = new String(credDecoded, StandardCharsets.UTF_8);
    // credentials = username:password
    final String[] values = credentials.split(":", 2);

    Optional<User> user = userRepository.findById(values[0]);

    if (!user.isPresent()) {
      throw new NotFoundException("User with nick " + values[0] + " does not exist");
    }

    if (!user.get().checkPassword(values[1])) {
      throw new UnauthorizedException("Incorrect password");
    }

    userRepository.delete(user.get());

    return ResponseEntity.ok().build();
  }

  @GetMapping("/users/{nick}/forms")
  public List<Form> getUserForms(@PathVariable String nick) {
    // TODO: password verify
    // List<Form> = userRepository.findById(nick).get().getForms();
    // return ;
    return null;
  }
}
