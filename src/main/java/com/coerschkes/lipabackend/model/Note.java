package com.coerschkes.lipabackend.model;

import jakarta.persistence.Entity;

@Entity
public class Note extends BaseEntity {
    private String title;
    private String category;
    private String text;

    public Note(final String title, final String category, final String text) {
        this.title = title;
        this.category = category;
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public String getText() {
        return text;
    }

    public Note updateWith(final Note note) {
        this.title = note.getTitle();
        this.category = note.getCategory();
        this.text = note.getText();
        return this;
    }
}
