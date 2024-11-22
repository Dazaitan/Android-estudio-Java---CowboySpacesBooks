package com.example.cowboyspacesbooks.vista;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cowboyspacesbooks.Home;
import com.example.cowboyspacesbooks.R;
import com.example.cowboyspacesbooks.SelectorPaginasBottomSheet;

public class SesionLectura extends AppCompatActivity implements SelectorPaginasBottomSheet.AlSeleccionarPaginaListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sesion_lectura);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            Button btnSave = findViewById(R.id.btn_save);
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("SesionLectura", "Sesion guardada correctamente");
                    startActivity(new Intent(SesionLectura.this, Home.class));
                }
            });
            //Despliegue de la ventana emergente
            Button btnLeerPaginas = findViewById(R.id.btn_leer_paginas);
            int paginasLeidas = 7;
            int totalPaginas = 178;
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