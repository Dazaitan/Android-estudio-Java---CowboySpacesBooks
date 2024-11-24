package com.example.cowboyspacesbooks.modelo;

public class SesionLectura {
    long tiempoTotal;
    String libroId;

    public SesionLectura(String libroId, long totalTime) {
        this.libroId = libroId;
        this.tiempoTotal = totalTime;
    }

    public String getLibroId() {
        return libroId;
    }

    public void setLibroId(String libroId) {
        this.libroId = libroId;
    }

    public long getTiempoTotal() {
        return tiempoTotal;
    }

    public void setTiempoTotal(long tiempoTotal) {
        this.tiempoTotal = tiempoTotal;
    }
}
