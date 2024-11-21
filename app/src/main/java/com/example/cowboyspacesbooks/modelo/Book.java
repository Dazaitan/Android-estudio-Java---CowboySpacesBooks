package com.example.cowboyspacesbooks.modelo;

import java.io.Serializable;

public class Book implements Serializable {
    private String title;
    private String coverImageUrl;

    // Constructor
    public Book(String title, String coverImageUrl) {
        this.title = title;
        this.coverImageUrl = coverImageUrl;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    // Setters (opcional, si necesitas cambiar los valores)
    public void setTitle(String title) {
        this.title = title;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }
}

