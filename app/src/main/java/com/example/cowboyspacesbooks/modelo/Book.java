package com.example.cowboyspacesbooks.modelo;

import java.io.Serializable;

public class Book implements Serializable {
    private String titulo,editorial,autor,formato,descripcion,estado;
    private long isbn;
    private int nPaginas;
    private String coverImageUrl;//portada
    // Constructor
    public Book(String title, String editorial, String autor, String formato, String descripcion, String estado, long isbn, int nPaginas, String coverImageUrl) {
        this.titulo = title;
        this.editorial = editorial;
        this.autor = autor;
        this.formato = formato;
        this.descripcion = descripcion;
        this.estado = estado;
        this.isbn = isbn;
        this.nPaginas = nPaginas;
        this.coverImageUrl = coverImageUrl;
    }

    // Getters
    public String getTitulo() {
        return titulo;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    // Setters (opcional, si necesitas cambiar los valores)
    public void setTitulo(String title) {
        this.titulo = titulo;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }
}

