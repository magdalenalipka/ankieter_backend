package com.example.ankieter.controller;

import com.example.ankieter.exception.ConflictException;
import com.example.ankieter.model.User;
import com.example.ankieter.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import javax.validation.Valid;

@RestController
public class UserController {

  @Autowired
  private UserRepository userRepository;

  @PostMapping("/users")
  public User addUser(@Valid @RequestBody User user) {
    Optional<User> conflictUser = userRepository.findById(user.getNick());
    if (conflictUser != null) {
      throw new ConflictException("User with nick " + user.getNick() + " already exists");
    }
    return userRepository.save(user);
  }

  @DeleteMapping("/users/{nick}")
  public ResponseEntity<?> deleteAnswer(@PathVariable String nick,
      @RequestParam String password) {

    userRepository.deleteById(nick);
    return ResponseEntity.ok().build();
  }
}
