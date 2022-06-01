package com.example.ankieter.repository;

import com.example.ankieter.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String>, UserRepositoryCustom {
}
