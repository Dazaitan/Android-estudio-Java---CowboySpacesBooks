package com.example.cowboyspacesbooks.modelo;

public class Listas {
    private int lista_Id;
    private String nameList,descripcion,portada;;

    public Listas(int lista_Id, String nameList, String descripcion, String portada) {
        this.lista_Id = lista_Id;
        this.nameList = nameList;
        this.descripcion = descripcion;
        this.portada = portada;
    }

    public Listas(int lista_Id, String nameList, String descripcion) {
        this.lista_Id = lista_Id;
        this.nameList = nameList;
        this.descripcion = descripcion;
    }

    public int getLista_Id() {
        return lista_Id;
    }

    public void setLista_Id(int lista_Id) {
        this.lista_Id = lista_Id;
    }

    public String getNameList() {
        return nameList;
    }

    public void setNameList(String nameList) {
        this.nameList = nameList;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPortada() {
        return portada;
    }

    public void setPortada(String portada) {
        this.portada = portada;
    }
}
