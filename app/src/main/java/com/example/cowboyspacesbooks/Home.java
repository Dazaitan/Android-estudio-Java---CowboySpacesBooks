package com.example.cowboyspacesbooks;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cowboyspacesbooks.modelo.Book;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {
    private BookAdapter adapter;
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

            List<Book> listaDeLibros = new ArrayList<>();
            listaDeLibros.add(new Book("Noches blancas", "https://imagessl7.casadellibro.com/a/l/s5/47/9788416440047.webp"));
            listaDeLibros.add(new Book("Almendra", "https://images.cdn2.buscalibre.com/fit-in/360x360/be/e2/bee26d4d07f382b1aee010b652eeb4ff.jpg"));
            listaDeLibros.add(new Book("Almendra", "https://images.cdn2.buscalibre.com/fit-in/360x360/be/e2/bee26d4d07f382b1aee010b652eeb4ff.jpg"));
            listaDeLibros.add(new Book("Almendra", "https://images.cdn2.buscalibre.com/fit-in/360x360/be/e2/bee26d4d07f382b1aee010b652eeb4ff.jpg"));
            listaDeLibros.add(new Book("Almendra", "https://images.cdn2.buscalibre.com/fit-in/360x360/be/e2/bee26d4d07f382b1aee010b652eeb4ff.jpg"));

            // Configura tu adaptador
            BookAdapter adapter = new BookAdapter(this, listaDeLibros);
            recyclerView.setAdapter(adapter);

            adapter.setOnItemClickListener(new BookAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Log.d("Logros","Ingreso al onclick del recycler de Home");
                    Book clickedBook = listaDeLibros.get(position);
                    Intent intent = new Intent(Home.this, VistaPrevia.class);
                    intent.putExtra("titulo", clickedBook.getTitle());
                    intent.putExtra("imagenUrl", clickedBook.getCoverImageUrl());

                    // Agrega un extra para el contexto
                    intent.putExtra("contexto", "pruebas"); // Cambia "value" por el nombre del contexto adecuado
                    startActivity(intent);
                }
            });


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
    public void onCardClick(View view) {
        startActivity(new Intent(Home.this, AgregarLibro.class));
    }
}