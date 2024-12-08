package com.example.cowboyspacesbooks.modelo;

public class Listas {
    private int lista_Id;
    private String nameList;
    private boolean isSelected;

    public Listas(String nameList, boolean isSelected) {
        this.nameList = nameList;
        this.isSelected = false;
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

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
