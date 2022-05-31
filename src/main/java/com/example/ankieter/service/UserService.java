package com.example.ankieter.service;

import com.example.ankieter.exception.ResourceNotFoundException;
import com.example.ankieter.model.User;
import com.example.ankieter.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

  @Autowired
  private UserRepository repository;

  @Override
  public User findByNick(String nick) {
    var user = repository.findById(nick);
    return user.orElseThrow(() -> new ResourceNotFoundException("User not found with id " + nick));
  }
}