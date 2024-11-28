package com.example.cowboyspacesbooks.modelo;

import java.io.Serializable;

public class Book implements Serializable {
    private String titulo,editorial,autor,formato,descripcion,estado;
    private String isbn;
    private String nPaginas,pagsLeidas;
    private String portada;//portada
    // Constructor

    public Book() {
    }

    public Book(String titulo, String isbn, String coverImageUrl) {
        this.titulo = titulo;
        this.isbn = isbn;
        this.portada = coverImageUrl;
    }

    public Book(String title, String editorial, String autor, String formato, String descripcion, String estado, String isbn, String nPaginas, String pagsLeidas, String coverImageUrl) {
        this.titulo = title;
        this.editorial = editorial;
        this.autor = autor;
        this.formato = formato;
        this.descripcion = descripcion;
        this.estado = estado;
        this.isbn = isbn;
        this.nPaginas = nPaginas;
        this.pagsLeidas = pagsLeidas;
        this.portada = coverImageUrl;
    }

    public Book(String titulo, String autor, String isbn, String portada) {
        this.titulo=titulo;
        this.autor=autor;
        this.isbn=isbn;
        this.portada=portada;
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

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getnPaginas() {
        return nPaginas;
    }

    public void setnPaginas(String nPaginas) {
        this.nPaginas = nPaginas;
    }

    public String getPagsLeidas() {
        return pagsLeidas;
    }

    public void setPagsLeidas(String pagsLeidas) {
        this.pagsLeidas = pagsLeidas;
    }
}

