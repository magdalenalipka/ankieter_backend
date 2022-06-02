package com.example.ankieter.model;

import javax.persistence.*;

@Entity
public class MultiChoiceAnswer extends Answer {

    MultiChoiceAnswer() {
        this.type = "multiChoice";
    }

    @Column(name = "choice_indices", columnDefinition = "text")
    private String choiceIndices;

    public String getChoice() {
        return this.choiceIndices;
    }

    public void setChoice(int[] indices) {
        // TODO: implement stringify
        this.choiceIndices = "[]";
    }

    public String getType() {
        return this.type;
    }
}
