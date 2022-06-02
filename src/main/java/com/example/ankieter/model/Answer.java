package com.example.ankieter.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "answers")
public abstract class Answer extends AuditModel {

    @Column(columnDefinition = "text", name = "question_id")
    private String questionId;

    @NotBlank
    @Column(columnDefinition = "text")
    protected String type;

    public String getType() {
        return this.type;
    }
}
