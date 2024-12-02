package com.example.cowboyspacesbooks.controlador;

import com.example.cowboyspacesbooks.modelo.Book;
import com.example.cowboyspacesbooks.modelo.LibroLista;
import com.example.cowboyspacesbooks.modelo.Listas;
import com.example.cowboyspacesbooks.modelo.Notes;
import com.example.cowboyspacesbooks.modelo.SesionLectura;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

import java.util.List;
public interface ApiService {
    @GET("libros/GetVistaPrevia.php")
    Call<List<Book>> obtenerDetallesLibros(@Query("isbn") String isbn);
    //obtener libros dependiente del estado que se defina
    @GET("libros/GetLibrosPorEstado.php")
    Call<List<Book>> obtenerLibrosLeidos(@Query("estado") String estado);
    //obtener una lista de libros
    @GET("libros/GetListaLibros.php")
    Call<List<Book>> obtenerLibros();
    //obtener todas las notas
    @GET("notas/GetObtenerNotas.php")
    Call<List<Notes>> obtenerNotas();
    //Insetar notas
    @POST("Notas/PostInsertarNotas.php")
    Call<Void> insertarNota(@Body Notes note);
    //Insetar sesion de lectura
    @POST("sesionlectura/PostSesionLectura.php")
    Call<Void> enviarSesionDeLectura(@Body SesionLectura sesionLectura);
    @POST("listas/PostInsertarLista.php")
    Call<Void> insertarLista(@Body Listas lista);

    @PUT("libros/PutActualizarLibro.php")
    Call<Void> actualizarLibro(@Body Book libro);

    @DELETE("libros/DeleteEliminarLibro.php")
    Call<Void> eliminarLibro(@Query("isbn") String isbn);

    @GET("listas/GetListas.php")
    Call<List<Listas>> obtenerListas();
    
    @GET("listas/GetrelListasLibros.php")
    Call<List<LibroLista>> obtenerLibrosListas(@Query("lista_Id") String listaId);
}
