package com.example.cowboyspacesbooks.controlador;
import com.example.cowboyspacesbooks.modelo.Notes;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IPostNotas {
    @POST("Notas/PostInsertarNotas.php")
    Call<Void> insertarNota(@Body Notes note);
}
