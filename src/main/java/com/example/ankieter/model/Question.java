package com.example.ankieter.model;

import com.google.gson.*;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "questions")
public abstract class Question extends AuditModel {
    @NotBlank
    @Size(min = 1)
    private String title;

    @Column(columnDefinition = "text")
    private String description;

    @NotBlank
    @Column(columnDefinition = "text")
    protected String type;

    @NotBlank
    @Column(name = "form_id", columnDefinition = "text")
    protected String formId;

    @Column(name = "answers")
    private String answers; // json z tablicą odpowiedzi

    @Column(name = "required", columnDefinition = "boolean")
    private Boolean required;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return this.type;
    }

    public Boolean getRequired() {
        return this.required == null ? false : this.required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public void setAnswers(String[] answers) {
        this.answers = new Gson().toJson(answers);
    }

    public String[] getAnswers() {
        return new Gson().fromJson(answers, String[].class);
    }

    public String getFormId() {
        return this.formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

}
