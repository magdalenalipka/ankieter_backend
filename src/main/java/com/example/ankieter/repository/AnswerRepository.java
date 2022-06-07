package com.example.ankieter.repository;

import com.example.ankieter.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, String> {
  @Query(value = "select * from answers join questions on answers.question_id = questions.id where questions.form_id = ?1", nativeQuery = true)
  public List<Answer> getByFormId(String formId);
}
