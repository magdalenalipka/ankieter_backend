package com.example.ankieter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "answers")
public abstract class Answer extends AuditModel {
    @Id
    @GeneratedValue(generator = "answer_generator")
    @SequenceGenerator(name = "answer_generator", sequenceName = "answer_sequence", initialValue = 1000)
    private Long id;

    @Column(columnDefinition = "choice")
    private int index;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "question_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Question question;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getChoice() {
        return this.index;
    }

    public void setChoice(int index) {
        this.index = index;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
