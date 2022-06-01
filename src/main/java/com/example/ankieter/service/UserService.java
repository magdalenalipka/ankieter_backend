package com.example.ankieter.service;

import java.util.Optional;

import com.example.ankieter.exception.ResourceNotFoundException;
import com.example.ankieter.model.User;
import com.example.ankieter.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

  @Autowired
  private UserRepository repository;

  public User findByNick(String nick) {
    Optional<User> user = repository.findById(nick);
    return user.orElseThrow(() -> new ResourceNotFoundException("User not found with id " + nick));
  }

}