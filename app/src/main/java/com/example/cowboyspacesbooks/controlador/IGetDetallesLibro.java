package com.example.cowboyspacesbooks.controlador;

import com.example.cowboyspacesbooks.modelo.Book;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IGetDetallesLibro {
    @GET("libros/GetVistaPrevia.php")
    Call<List<Book>> obtenerDetallesLibros(@Query("isbn") String isbn);
}
