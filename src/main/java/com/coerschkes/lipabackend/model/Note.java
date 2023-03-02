package com.coerschkes.lipabackend.model;

import jakarta.persistence.Entity;

@Entity
public class Note extends BaseEntity {
    private final String title;
    private final String category;
    private final String text;

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
}
