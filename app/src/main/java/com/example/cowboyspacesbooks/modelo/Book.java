package com.example.cowboyspacesbooks.modelo;

public class Book {
    private String title;
    private String coverImageUrl; // URL o path de la imagen de la portada

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

