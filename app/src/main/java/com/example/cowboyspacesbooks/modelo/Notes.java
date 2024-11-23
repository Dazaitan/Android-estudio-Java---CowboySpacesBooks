package com.example.cowboyspacesbooks.modelo;

public class Notes {
    String isbn,tipoNota,cuerpo;
    int pagInicio,pagFinal;

    public Notes(String isbn, String tipoNota, String cuerpo, int pagInicio, int pagFinal) {
        this.isbn = isbn;
        this.tipoNota = tipoNota;
        this.cuerpo = cuerpo;
        this.pagInicio = pagInicio;
        this.pagFinal = pagFinal;
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
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
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
