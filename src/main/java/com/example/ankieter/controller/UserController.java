package com.example.ankieter.controller;

import com.example.ankieter.model.Form;
import com.example.ankieter.model.User;
import com.example.ankieter.repository.UserRepository;
import com.example.ankieter.utilities.Headers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@CrossOrigin(methods = { RequestMethod.DELETE, RequestMethod.GET, RequestMethod.OPTIONS,
    RequestMethod.POST }, allowedHeaders = "*")
@RestController
public class UserController {

  @Autowired
  private UserRepository userRepository;

  @PostMapping("/users")
  public ResponseEntity addUser(@RequestHeader("Authorization") String auth, @RequestHeader("Origin") String origin) {

    System.out.println(origin);
    String base64Credentials = auth.substring("Basic".length()).trim();
    byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
    String credentials = new String(credDecoded, StandardCharsets.UTF_8);
    // credentials = username:password
    String[] values = credentials.split(":", 2);

    Optional<User> conflictUser = userRepository.findById(values[0]);
    if (conflictUser.isPresent()) {
      return ResponseEntity.status(409).headers(new Headers(origin)).body("This nick is taken.");
    }

    User newUser = new User();
    newUser.setNick(values[0]);
    newUser.setPassword(values[1]);

    User savedUser = userRepository.save(newUser);

    return ResponseEntity.status(201).headers(new Headers(origin)).body(savedUser);
  }

  @RequestMapping(value = "/users", method = RequestMethod.OPTIONS)
  public ResponseEntity checkUser(@RequestHeader("Authorization") String auth, @RequestHeader("Origin") String origin) {

    System.out.println(origin);

    User user = userRepository.getUserFromAuth(auth);

    if (user == null) {
      return ResponseEntity.status(403).headers(new Headers(origin)).build();
    }

    System.out.print(user.getNick());

    return ResponseEntity.ok().headers(new Headers(origin)).build();
  }

  @DeleteMapping("/users")
  public ResponseEntity<?> deleteAnswer(@RequestHeader("Authorization") String auth,
      @RequestHeader("Origin") String origin) {
    User user = userRepository.getUserFromAuth(auth);
    if (user == null) {
      return ResponseEntity.status(403).headers(new Headers(origin)).build();
    }

    userRepository.delete(user);

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
