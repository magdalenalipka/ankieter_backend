package com.example.ankieter.repository;

import com.example.ankieter.model.Form;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FormRepository extends JpaRepository<Form, String> {
  @Query(value = "select * from forms where password is null", nativeQuery = true)
  public List<Form> getAllPublicForms();

  @Query(value = "select * from forms where user_id = ?1", nativeQuery = true)
  public List<Form> getUserForms(String userId);
}
