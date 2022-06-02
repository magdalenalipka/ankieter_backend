package com.example.ankieter.model;

import javax.persistence.*;

@Entity
public class MultiChoiceQuestion extends Question {

    MultiChoiceQuestion() {
        this.type = "multiChoice";
    }

}
