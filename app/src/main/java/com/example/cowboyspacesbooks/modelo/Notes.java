package com.example.cowboyspacesbooks.modelo;

public class Notes {
    private String isbn,tipoNota,descripcion,fecha,imagenUrl,titulo,autor;
    private int pagInicio,pagFinal;

    public Notes(String isbn, String tipoNota, String cuerpo, int pagInicio, int pagFinal) {
        this.isbn = isbn;
        this.tipoNota = tipoNota;
        this.descripcion = cuerpo;
        this.pagInicio = pagInicio;
        this.pagFinal = pagFinal;
    }

    public Notes(String tipoNota, String descripcion, String fecha, String imagenUrl, String titulo, String autor, int pagInicio, int pagFinal) {
        this.tipoNota = tipoNota;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.imagenUrl = imagenUrl;
        this.titulo = titulo;
        this.autor = autor;
        this.pagInicio = pagInicio;
        this.pagFinal = pagFinal;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTipoNota() {
        return tipoNota;
    }

    public void setTipoNota(String tipoNota) {
        this.tipoNota = tipoNota;
    }

    public String getCuerpo() {
        return descripcion;
    }

    public void setCuerpo(String cuerpo) {
        this.descripcion = cuerpo;
    }

    public int getPagInicio() {
        return pagInicio;
    }

    public void setPagInicio(int pagInicio) {
        this.pagInicio = pagInicio;
    }

    public int getPagFinal() {
        return pagFinal;
    }

    public void setPagFinal(int pagFinal) {
        this.pagFinal = pagFinal;
    }
}
