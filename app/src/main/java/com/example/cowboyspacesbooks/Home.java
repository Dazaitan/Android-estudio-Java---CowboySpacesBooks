package com.example.cowboyspacesbooks;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cowboyspacesbooks.controlador.IGetListaLibros;
import com.example.cowboyspacesbooks.controlador.RetrofitClient;
import com.example.cowboyspacesbooks.modelo.Book;
import com.example.cowboyspacesbooks.vista.AgregarLibro;
import com.example.cowboyspacesbooks.vista.Logros;
import com.example.cowboyspacesbooks.vista.Memorizar;
import com.example.cowboyspacesbooks.vista.VistaPrevia;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Home extends AppCompatActivity {
    private BookAdapter adapter;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            //CARGUE DE ICONOS PREVIEW DE MANERA HORIZONTAL
            RecyclerView recyclerView = findViewById(R.id.iconPreview_recyclerView);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(layoutManager);

            /*List<Book> listaDeLibros = new ArrayList<>();
            listaDeLibros.add(new Book("Noches blancas", 1432432, "https://imagessl7.casadellibro.com/a/l/s5/47/9788416440047.webp"));
            listaDeLibros.add(new Book("Almendra", 10213213,"https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1616927575i/57551565.jpg"));

            // Configura tu adaptador
            BookAdapter adapter = new BookAdapter(this, listaDeLibros);
            recyclerView.setAdapter(adapter);

            adapter.setOnItemClickListener(new BookAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Log.d("Logros","Ingreso al onclick del recycler de Home");
                    Book clickedBook = listaDeLibros.get(position);
                    Intent intent = new Intent(Home.this, VistaPrevia.class);
                    intent.putExtra("titulo", clickedBook.getTitulo());
                    intent.putExtra("imagenUrl", clickedBook.getCoverImageUrl());
                    intent.putExtra("isbn", clickedBook.getIsbn());
                    // Agrega un extra para el contexto
                    intent.putExtra("contexto", "pruebas"); // Cambia "value" por el nombre del contexto adecuado
                    startActivity(intent);
                }
            });*/
            cargarLibrosDesdeServidor();

            BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    if (item.getItemId() == R.id.nav_home) {
                        // Ya estás en la actividad principal
                        return true;
                    } else if (item.getItemId() == R.id.nav_memorizar) {
                        startActivity(new Intent(Home.this, Memorizar.class));
                        return true;
                    } else if (item.getItemId() == R.id.nav_logros) {
                        startActivity(new Intent(Home.this, Logros.class));
                        return true;
                    } else if(item.getItemId() == R.id.nav_profile){
                        return true;
                    }
                    return false;
                }
            });
            return insets;
        });
    }
    private void cargarLibrosDesdeServidor() {
        IGetListaLibros bookApi = RetrofitClient.getClient().create(IGetListaLibros.class);

        bookApi.obtenerLibros().enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Book> listaDeLibros = response.body();
                    // Configura el adaptador con los datos recibidos
                    RecyclerView recyclerView = findViewById(R.id.iconPreview_recyclerView);
                    adapter = new BookAdapter(Home.this, listaDeLibros);
                    recyclerView.setAdapter(adapter);

                    // Configurar el listener para clics
                    adapter.setOnItemClickListener(new BookAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            Book clickedBook = listaDeLibros.get(position);
                            Intent intent = new Intent(Home.this, VistaPrevia.class);
                            intent.putExtra("titulo", clickedBook.getTitulo());
                            intent.putExtra("imagenUrl", clickedBook.getCoverImageUrl());
                            intent.putExtra("isbn", clickedBook.getIsbn());
                            intent.putExtra("contexto", "pruebas");
                            startActivity(intent);
                        }
                    });
                } else {
                    Toast.makeText(Home.this, "Error al cargar los datos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                Log.e("Home", "Error al conectar con el servidor", t);
                Toast.makeText(Home.this, "Error al conectar con el servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void onCardClick(View view) {
        startActivity(new Intent(Home.this, AgregarLibro.class));
    }
}