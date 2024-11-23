package com.example.cowboyspacesbooks.controlador;

import com.example.cowboyspacesbooks.modelo.Book;
import com.example.cowboyspacesbooks.modelo.Notes;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import java.util.List;
public interface ApiService {
    @GET("libros/GetVistaPrevia.php")
    Call<List<Book>> obtenerDetallesLibros(@Query("isbn") String isbn);

    @GET("libros/GetLibrosPorEstado.php")
    Call<List<Book>> obtenerLibrosLeidos(@Query("estado") String estado);

    @GET("libros/GetListaLibros.php")
    Call<List<Book>> obtenerLibros();

    @POST("Notas/PostInsertarNotas.php")
    Call<Void> insertarNota(@Body Notes note);
}
