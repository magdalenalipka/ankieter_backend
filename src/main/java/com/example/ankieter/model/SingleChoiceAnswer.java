package com.example.ankieter.model;

import javax.persistence.*;

@Entity
public class SingleChoiceAnswer extends Answer {

    SingleChoiceAnswer() {
        this.type = "singleChoice";
    }

    @Column(name = "choice_index", columnDefinition = "int")
    private int choiceIndex;

    public int getChoice() {
        return this.choiceIndex;
    }

    public void setChoice(int index) {
        this.choiceIndex = index;
    }

    public String getType() {
        return this.type;
    }
}
