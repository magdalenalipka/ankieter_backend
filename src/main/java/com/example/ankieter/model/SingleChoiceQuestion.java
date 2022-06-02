package com.example.ankieter.model;

import javax.persistence.*;

@Entity
public class SingleChoiceQuestion extends Question {

    SingleChoiceQuestion() {
        this.type = "singleChoice";
    }

    public String getType() {
        return this.type;
    }
}
