package com.sabihamumcu.kelimekutusu.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Word implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String word;
    private String description;

    public Word() {

    }

    public Word(String word, String description) {

        this.word = word;
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getWord() {
        return word;
    }

    public String getDescription() {
        return description;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
