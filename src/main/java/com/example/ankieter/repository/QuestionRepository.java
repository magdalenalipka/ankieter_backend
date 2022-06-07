package com.example.ankieter.repository;

import com.example.ankieter.model.Question;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, String> {
  @Query(value = "select * from questions where form_id = ?1 and type = ?2", nativeQuery = true)
  public <T extends Question> List<T> getFormQuestions(String formId, String type);

  @Query(value = "select * from questions where form_id = ?1 and required = true and type = ?2", nativeQuery = true)
  public <T extends Question> List<T> getFormRequiredQuestions(String formId, String type);
}
