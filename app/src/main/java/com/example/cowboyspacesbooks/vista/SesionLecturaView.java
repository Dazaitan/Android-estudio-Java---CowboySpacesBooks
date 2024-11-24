package com.example.cowboyspacesbooks.vista;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cowboyspacesbooks.Home;
import com.example.cowboyspacesbooks.R;
import com.example.cowboyspacesbooks.SelectorPaginasBottomSheet;
import com.example.cowboyspacesbooks.controlador.ApiService;
import com.example.cowboyspacesbooks.controlador.RetrofitClient;
import com.example.cowboyspacesbooks.modelo.SesionLectura;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SesionLecturaView extends AppCompatActivity implements SelectorPaginasBottomSheet.AlSeleccionarPaginaListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sesion_lectura);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            Intent intent = getIntent();
            String isbn = intent.getStringExtra("isbn");
            String numPaginas = intent.getStringExtra("numPaginas");
            String pagLeidas = intent.getStringExtra("pagsLeidas");
            TextView tiempoUserFormat = findViewById(R.id.tv_read_time);
            long totalTiempo = intent.getLongExtra("tiempo",0L);
            Button btnSave = findViewById(R.id.btn_save);
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SesionLectura sesionLectura = new SesionLectura(isbn,totalTiempo);
                    ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
                    Gson gson = new Gson();
                    String jsonBody = gson.toJson(sesionLectura);
                    Log.d("JSON Enviado", jsonBody);
                    Call<Void> call = apiService.enviarSesionDeLectura(sesionLectura);
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                Log.d("SesionLectura", "Sesión de lectura enviada exitosamente.");
                                Toast.makeText(SesionLecturaView.this, "Sesión guardada correctamente", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SesionLecturaView.this, Home.class));
                            } else {
                                Log.e("SesionLectura", "Error al guardar la sesión. Código: " + response.code());
                                Toast.makeText(SesionLecturaView.this, "Error al guardar la sesión", Toast.LENGTH_SHORT).show();
                            }

                        }
                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Log.e("SesionLectura", "Error al conectar con el servidor", t);
                            Toast.makeText(SesionLecturaView.this, "Error al conectar con el servidor", Toast.LENGTH_SHORT).show();
                        }
                    });
                    //
                }
            });
            // Formatear el tiempo en hh:mm:ss para la interfaz de usuario
            int seconds = (int) (totalTiempo / 1000) % 60; // Segundos
            int minutes = (int) ((totalTiempo / (1000 * 60)) % 60); // Minutos
            int hours = (int) (totalTiempo / (1000 * 60 * 60)); // Horas
            String tiempoFormateado = String.format("%02d:%02d:%02d", hours, minutes, seconds);
            tiempoUserFormat.setText(tiempoFormateado);
            //Despliegue de la ventana emergente
            Button btnLeerPaginas = findViewById(R.id.btn_leer_paginas);
            int paginasLeidas = Integer.parseInt(pagLeidas);
            int totalPaginas = Integer.parseInt(numPaginas);
            btnLeerPaginas.setText("pags. " + paginasLeidas + " / " + totalPaginas);
            btnLeerPaginas.setOnClickListener(view -> {
                 // Número total de páginas
                SelectorPaginasBottomSheet bottomSheet = SelectorPaginasBottomSheet.nuevaInstancia(totalPaginas);
                bottomSheet.show(getSupportFragmentManager(), "SelectorPaginasBottomSheet");
            });

            ImageButton btnBack = findViewById(R.id.btn_back);
            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish(); // Cierra la actividad actual y vuelve a la anterior
                }
            });
            return insets;
        });
    }

    @Override
    public void onPaginaSeleccionada(int paginaSeleccionada) {
        // Manejar la página seleccionada del BottomSheet
        TextView btnLeerPaginas = findViewById(R.id.btn_leer_paginas);
        btnLeerPaginas.setText("pags. " + paginaSeleccionada + " / 178"); // Actualizar el texto
    }
}