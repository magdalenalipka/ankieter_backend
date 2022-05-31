package com.example.ankieter.repository;

import com.example.ankieter.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
  @Query("SELECT u FROM User u WHERE u.nick = ?1")
  User findByNick(String nick);
}
