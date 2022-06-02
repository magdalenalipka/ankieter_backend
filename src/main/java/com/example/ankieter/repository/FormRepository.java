package com.example.ankieter.repository;

import com.example.ankieter.model.Form;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FormRepository extends JpaRepository<Form, String> {
  @Query(value = "select f from forms f where password is null", nativeQuery = true)
  public List<Form> getAllPublicForms();
}
