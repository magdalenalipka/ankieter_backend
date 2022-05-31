package com.example.ankieter.service;

import com.example.ankieter.model.User;

public interface IUserService {
  public User findByNick(String nick);
}
