package com.example.ankieter.repository;

import com.example.ankieter.model.User;

public interface UserRepositoryCustom {
  User getUserFromAuth(String auth);
}
