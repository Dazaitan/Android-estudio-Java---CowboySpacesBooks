package com.example.cowboyspacesbooks.controlador;

import com.example.cowboyspacesbooks.modelo.Book;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IgetLibrosLeidos {
    @GET("libros/GetLibrosPorEstado.php")
    Call<List<Book>> obtenerLibrosLeidos(@Query("estado") String estado);
}
