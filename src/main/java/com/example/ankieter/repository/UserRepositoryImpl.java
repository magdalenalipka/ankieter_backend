package com.example.ankieter.repository;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.ankieter.model.User;

@Component
public class UserRepositoryImpl implements UserRepositoryCustom {
  @Autowired
  UserRepository userRepository;

  public User getUserFromAuth(String auth) {
    String base64Credentials = auth.substring("Basic".length()).trim();
    byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
    String credentials = new String(credDecoded, StandardCharsets.UTF_8);
    String[] values = credentials.split(":", 2);

    Optional<User> user = userRepository.findById(values[0]);

    if (!user.isPresent()) {
      return null;
    }

    if (!user.get().checkPassword(values[1])) {
      return null;
    }

    return user.get();
  }
}