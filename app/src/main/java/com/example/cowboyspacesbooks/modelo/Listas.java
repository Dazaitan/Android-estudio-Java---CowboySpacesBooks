package com.example.cowboyspacesbooks.modelo;

public class Listas {
    private int lista_Id;
    private String nameList;

    public Listas(String nameList) {
        this.nameList = nameList;
    }

    public Listas(int lista_Id, String nameList) {
        this.lista_Id = lista_Id;
        this.nameList = nameList;
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
}
