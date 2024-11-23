package com.example.cowboyspacesbooks.vista;

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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.cowboyspacesbooks.R;
import com.example.cowboyspacesbooks.controlador.IGetDetallesLibro;
import com.example.cowboyspacesbooks.controlador.RetrofitClient;
import com.example.cowboyspacesbooks.modelo.Book;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
            FloatingActionButton btnAddNote = findViewById(R.id.btn_add_note);
            // Obtén los extras del Intent
            String titulo = getIntent().getStringExtra("titulo");
            String imagenUrl = getIntent().getStringExtra("imagenUrl");
            String isbn = getIntent().getStringExtra("isbn");
            String contexto = getIntent().getStringExtra("contexto");

            cargarDetallesLibroAPI(isbn,imagenUrl);

            //Configuracion de boton btnTimer
            FloatingActionButton btnTimer = findViewById(R.id.btn_timer);
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
            //Mostrar o ocultar boton de timer dependiendo del contexto que se reciba como input
            if ("logros".equals(contexto)) {// Verifica el contexto y muestra oculta el segundo botón
                Log.d("VistaPrevia","Ingreso a la actividad");
                // Muestra el segundo botón si el contexto es "logros"
                btnTimer.setVisibility(View.VISIBLE);
            } else if ("pruebas".equals(contexto)){
                btnTimer.setVisibility(View.GONE);
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
    private void cargarDetallesLibroAPI(String isbn, String imagenUrl) {
        IGetDetallesLibro bookApi = RetrofitClient.getClient().create(IGetDetallesLibro.class);
        FloatingActionButton btnTimer = findViewById(R.id.btn_timer);
        //btnTimer.setVisibility(View.GONE);
        bookApi.obtenerDetallesLibros(isbn).enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Book> listaDeLibros = response.body();

                    Book libro = listaDeLibros.get(0);
                    TextView tituloTextView = findViewById(R.id.tv_book_title);
                    TextView tvAuthorPublisher = findViewById(R.id.tv_author_and_publisher);
                    ImageView imagenImageView = findViewById(R.id.iv_book_cover);
                    TextView estadoTextView = findViewById(R.id.tv_status);
                    TextView descripcionTextView = findViewById(R.id.tv_description);
                    tituloTextView.setText(libro.getTitulo());
                    estadoTextView.setText(libro.getEstado());
                    descripcionTextView.setText(libro.getDescripcion());
                    tvAuthorPublisher.setText("Autor y editorial: " + libro.getAutor() + ", " + libro.getEditorial());
                    if (imagenUrl != null) {
                        Glide.with(VistaPrevia.this)
                                .load(imagenUrl)
                                .placeholder(R.drawable.ic_book_placeholder)
                                .error(R.drawable.error_image)
                                .into(imagenImageView);
                    }
                } else {
                    Toast.makeText(VistaPrevia.this, "Error al cargar los datos", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                Log.e("Home", "Error al conectar con el servidor", t);
                Toast.makeText(VistaPrevia.this, "Error al conectar con el servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }
}