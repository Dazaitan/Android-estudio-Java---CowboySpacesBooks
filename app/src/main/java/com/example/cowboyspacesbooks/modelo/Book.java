package com.example.cowboyspacesbooks.modelo;

import java.io.Serializable;

public class Book implements Serializable {
    private String titulo,editorial,autor,formato,descripcion,estado;
    private long isbn;
    private int nPaginas;
    private String portada;//portada
    // Constructor

    public Book() {
    }

    public Book(String titulo, long isbn, String coverImageUrl) {
        this.titulo = titulo;
        this.isbn = isbn;
        this.portada = coverImageUrl;
    }

    public Book(String title, String editorial, String autor, String formato, String descripcion, String estado, long isbn, int nPaginas, String coverImageUrl) {
        this.titulo = title;
        this.editorial = editorial;
        this.autor = autor;
        this.formato = formato;
        this.descripcion = descripcion;
        this.estado = estado;
        this.isbn = isbn;
        this.nPaginas = nPaginas;
        this.portada = coverImageUrl;
    }

    // Getters
    public String getTitulo() {
        return titulo;
    }

    public String getCoverImageUrl() {
        return portada;
    }

    // Setters (opcional, si necesitas cambiar los valores)
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.portada = coverImageUrl;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public long getIsbn() {
        return isbn;
    }

    public void setIsbn(long isbn) {
        this.isbn = isbn;
    }

    public int getnPaginas() {
        return nPaginas;
    }

    public void setnPaginas(int nPaginas) {
        this.nPaginas = nPaginas;
    }
}

