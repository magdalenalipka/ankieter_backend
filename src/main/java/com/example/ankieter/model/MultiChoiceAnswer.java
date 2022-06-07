package com.example.ankieter.model;

import com.google.gson.*;

import java.util.ArrayList;
import java.util.Arrays;

import javax.persistence.*;

@Entity
public class MultiChoiceAnswer extends Answer {

    MultiChoiceAnswer() {
        this.type = "multiChoice";
    }

    @Column(name = "choice_indices", columnDefinition = "text")
    private String choiceIndices;

    public int[] getChoice() {
        return Arrays.asList(new Gson().fromJson(this.choiceIndices, String[].class)).stream().mapToInt(Integer::new)
                .toArray();
    }

    public void setChoice(int[] indices) {

        ArrayList<String> choice = new ArrayList<String>(indices.length);

        for (int index : indices) {
            choice.add(Integer.toString(index));
        }

        this.choiceIndices = new Gson().toJson(choice);
    }

    public String getType() {
        return this.type;
    }
}
