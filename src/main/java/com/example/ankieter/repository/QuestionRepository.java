package com.example.ankieter.repository;

import com.example.ankieter.model.Question;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, String> {
  @Query(value = "select * from question where form_id = ?1", nativeQuery = true)
  public List<Question> getFormQuestions(String formId);

  @Query(value = "select * from question where form_id = ?1 and required = true", nativeQuery = true)
  public List<Question> getFormRequiredQuestions(String formId);
}
