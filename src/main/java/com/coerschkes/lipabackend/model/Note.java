package com.coerschkes.lipabackend.model;

import jakarta.persistence.Entity;

@Entity
public class Note extends BaseEntity {
    private String title;
    private String category;
    private String text;

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
