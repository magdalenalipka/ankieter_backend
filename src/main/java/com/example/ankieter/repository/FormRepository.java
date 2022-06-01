package com.example.ankieter.repository;

import com.example.ankieter.model.Form;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FormRepository extends JpaRepository<Form, String> {
}
