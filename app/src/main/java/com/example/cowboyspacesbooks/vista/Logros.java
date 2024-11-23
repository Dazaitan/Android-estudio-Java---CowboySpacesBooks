package com.example.cowboyspacesbooks.vista;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cowboyspacesbooks.BookAdapter;
import com.example.cowboyspacesbooks.Home;
import com.example.cowboyspacesbooks.R;
import com.example.cowboyspacesbooks.controlador.IGetListaLibros;
import com.example.cowboyspacesbooks.controlador.IgetLibrosLeidos;
import com.example.cowboyspacesbooks.controlador.RetrofitClient;
import com.example.cowboyspacesbooks.modelo.Book;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Logros extends AppCompatActivity {
    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;
    private List<Book> bookList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_logros);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            cargarLibrosDesdeServidor("Leido");
            //Navegacion
            BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    if (item.getItemId() == R.id.nav_home) {
                        startActivity(new Intent(Logros.this, Home.class));
                        return true;
                    } else if (item.getItemId() == R.id.nav_memorizar) {
                        startActivity(new Intent(Logros.this, Memorizar.class));
                        return true;
                    } else if (item.getItemId() == R.id.nav_logros) {
                        //Estoy en esta actividad
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
    private void cargarLibrosDesdeServidor(String estado) {
        IgetLibrosLeidos bookApi = RetrofitClient.getClient().create(IgetLibrosLeidos.class);

        bookApi.obtenerLibrosLeidos(estado).enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Book> listaDeLibros = response.body();
                    // Configura el adaptador con los datos recibidos
                    RecyclerView recyclerView = findViewById(R.id.recyclerView);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(Logros.this, LinearLayoutManager.HORIZONTAL, false);
                    recyclerView.setLayoutManager(layoutManager);

                    BookAdapter adapter = new BookAdapter(Logros.this, listaDeLibros);
                    recyclerView.setAdapter(adapter);

                    // Configurar el listener para clics
                    adapter.setOnItemClickListener(new BookAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            Book clickedBook = listaDeLibros.get(position);
                            Intent intent = new Intent(Logros.this, VistaPrevia.class);
                            intent.putExtra("titulo", clickedBook.getTitulo());
                            intent.putExtra("imagenUrl", clickedBook.getCoverImageUrl());
                            intent.putExtra("isbn", clickedBook.getIsbn());
                            intent.putExtra("contexto", "Logros");
                            startActivity(intent);
                        }
                    });
                } else {
                    Toast.makeText(Logros.this, "Error al cargar los datos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                Log.e("Home", "Error al conectar con el servidor", t);
                Toast.makeText(Logros.this, "Error al conectar con el servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }
}