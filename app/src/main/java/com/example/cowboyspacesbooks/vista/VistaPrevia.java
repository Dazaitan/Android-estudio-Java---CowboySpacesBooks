package com.example.cowboyspacesbooks.vista;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.cowboyspacesbooks.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class VistaPrevia extends AppCompatActivity {
    private FloatingActionButton btnAddNote;
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
            TextView tv_author_publisher = findViewById(R.id.tv_author_and_publisher);
            ImageView imagenImageView = findViewById(R.id.iv_book_cover);
            FloatingActionButton btnAddNote = findViewById(R.id.btn_add_note);
            // Obtén los extras del Intent
            String titulo = getIntent().getStringExtra("titulo");
            String imagenUrl = getIntent().getStringExtra("imagenUrl");
            String tv_autor_editorial = getIntent().getStringExtra("autor");
            String contexto = getIntent().getStringExtra("contexto");

            // Configura los datos en la vista
            if (titulo != null) {
                tituloTextView.setText(titulo);
                tv_author_publisher.setText(tv_autor_editorial);
            }

            if (imagenUrl != null) {
                Glide.with(this)
                        .load(imagenUrl)
                        .placeholder(R.drawable.ic_book_placeholder)
                        .error(R.drawable.error_image)
                        .into(imagenImageView);
            }
            //Configuracion de boton btnTimer
            FloatingActionButton btnTimer = findViewById(R.id.btn_timer);
            btnTimer.setVisibility(View.GONE);// Oculta inicialmente el segundo botón

            btnTimer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Abrir el modo lectura
                    Intent intent = new Intent(VistaPrevia.this, ModoLectura.class);
                    intent.putExtra("titulo", titulo);
                    intent.putExtra("imagenUrl", imagenUrl);
                    startActivity(intent);
                }
            });
            if ("logros".equals(contexto)) {// Verifica el contexto y muestra oculta el segundo botón
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

            //Boton para añadir notas
            btnAddNote = findViewById(R.id.btn_add_note);
            btnAddNote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(VistaPrevia.this, GestionNotasLayout.class));
                }
            });
            // Referencia al botón btn_back
            ImageButton btnBack = findViewById(R.id.btn_back);
            // Configurar el OnClickListener para devolver a la página anterior
            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish(); // Cierra la actividad actual y vuelve a la anterior
                }
            });
            return insets;
        });
    }
}