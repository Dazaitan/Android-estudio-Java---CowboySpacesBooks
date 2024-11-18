package com.example.cowboyspacesbooks;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.cowboyspacesbooks.vista.BarraInferiorHojaFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class VistaPrevia extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vista_previa);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            LinearLayout layoutSharedButtons = findViewById(R.id.layout_shared_buttons);
            TextView tituloTextView = findViewById(R.id.tv_book_title);
            ImageView imagenImageView = findViewById(R.id.iv_book_cover);
            FloatingActionButton btnTimer = findViewById(R.id.btn_timer);
            FloatingActionButton btnAddNote = findViewById(R.id.btn_add_note);
            // Oculta inicialmente el segundo botón
            btnTimer.setVisibility(View.GONE);
            // Obtén los extras del Intent
            String titulo = getIntent().getStringExtra("titulo");
            String imagenUrl = getIntent().getStringExtra("imagenUrl");
            String contexto = getIntent().getStringExtra("contexto");

            // Configura los datos en la vista
            if (titulo != null) {
                tituloTextView.setText(titulo);
            }

            if (imagenUrl != null) {
                Glide.with(this)
                        .load(imagenUrl)
                        .placeholder(R.drawable.ic_book_placeholder)
                        .error(R.drawable.error_image)
                        .into(imagenImageView);
            }

            // Verifica el contexto y muestra/oculta el segundo botón
            if ("logros".equals(contexto)) {
                Log.d("VistaPrevia","Ingreso a la actividad");
                // Muestra el segundo botón si el contexto es "logros"
                btnTimer.setVisibility(View.VISIBLE);
            } else if ("pruebas".equals(contexto)){
                Log.d("VistaPrevia", "Boton no habilitado");
            }

            //Desplegar menu de gestion de libros inferior
            ImageButton btnMenu = findViewById(R.id.btn_menu);
            btnMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BarraInferiorHojaFragment bottomSheetFragment = new BarraInferiorHojaFragment();
                    bottomSheetFragment.show(getSupportFragmentManager(), "BottomSheetDialog");
                }
            });
            return insets;
        });
    }
}