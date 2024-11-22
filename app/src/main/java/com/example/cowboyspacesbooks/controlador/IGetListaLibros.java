package com.example.cowboyspacesbooks.controlador;
import com.example.cowboyspacesbooks.modelo.Book;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
public interface IGetListaLibros {
    @GET("libros/GetListaLibros.php") // Cambia esto por la ruta completa de tu servidor
    Call<List<Book>> obtenerLibros();
}
