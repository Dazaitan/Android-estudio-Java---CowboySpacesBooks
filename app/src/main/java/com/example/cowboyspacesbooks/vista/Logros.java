package com.example.cowboyspacesbooks.vista;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

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
import com.example.cowboyspacesbooks.modelo.Book;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

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

            recyclerView = findViewById(R.id.recyclerView);

            // Configura el LinearLayoutManager con orientación horizontal
            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(layoutManager);

            // Cargar datos de ejemplo
            bookList = new ArrayList<>();
            bookList.add(new Book("Noches blancas", 10000000, "https://imagessl7.casadellibro.com/a/l/s5/47/9788416440047.webp"));
            // Agrega más libros según sea necesario

            // Configurar el adaptador
            bookAdapter = new BookAdapter(this,bookList);
            recyclerView.setAdapter(bookAdapter);
            //Establecer el listener de clics
            bookAdapter.setOnItemClickListener(new BookAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Log.d("Logros","Ingreso al onclick del recycler");
                    Book clickedBook = bookList.get(position);
                    Intent intent = new Intent(Logros.this, VistaPrevia.class);
                    intent.putExtra("titulo", clickedBook.getTitulo());
                    intent.putExtra("imagenUrl", clickedBook.getCoverImageUrl());
                    intent.putExtra("isbn", clickedBook.getIsbn());

                    // Agrega un extra para el contexto
                    intent.putExtra("contexto", "logros"); // Cambia "value" por el nombre del contexto adecuado
                    startActivity(intent);
                }
            });

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
}