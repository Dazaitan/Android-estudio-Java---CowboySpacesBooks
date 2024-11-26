package com.example.cowboyspacesbooks;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cowboyspacesbooks.modelo.Book;
import com.example.cowboyspacesbooks.modelo.Listas;
import com.example.cowboyspacesbooks.vista.AgregarLibro;
import com.example.cowboyspacesbooks.vista.AgregarListaBottomSheet;
import com.example.cowboyspacesbooks.vista.VistaPrevia;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class VisualizacionGeneral extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;
    private ListasAdapter listasAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizacion_general);

        // Configura insets para manejar el diseño
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Inicializa el RecyclerView
        recyclerView = findViewById(R.id.vistaLibrosColeccion);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        // Obtén el contexto del Intent
        //String contexto = getIntent().getStringExtra("contexto");
        String contexto = "libros";

        if (contexto == null) {
            Toast.makeText(this, "Contexto no especificado", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        // Decide qué mostrar según el contexto
        if (contexto.equals("libros")) {
            mostrarLibros();
        } else if (contexto.equals("listas")) {
            mostrarListas();
        } else {
            Toast.makeText(this, "Contexto desconocido", Toast.LENGTH_SHORT).show();
            finish();
        }
        FloatingActionButton btnAdd = findViewById(R.id.button_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (contexto.equals("libros")) {
                    Intent intent = new Intent(VisualizacionGeneral.this, AgregarLibro.class);
                    intent.putExtra("contexto","insertar");
                    startActivity(intent);
                } else if (contexto.equals("listas")) {
                    AgregarListaBottomSheet bottomSheet = new AgregarListaBottomSheet();
                    bottomSheet.setAddCollectionListener(collectionName -> {
                        // Aquí manejas la colección añadida
                        Toast.makeText(VisualizacionGeneral.this, "Colección añadida: " + collectionName, Toast.LENGTH_SHORT).show();
                        // Puedes actualizar tu RecyclerView o base de datos aquí
                    });
                    bottomSheet.show(getSupportFragmentManager(), "AddCollectionBottomSheet");
                }
            }
        });
    }

    private void mostrarLibros() {
        // Ejemplo de lista de libros
        List<Book> libros = new ArrayList<>();
        libros.add(new Book("Libro 1", "Autor 1", "https://example.com/libro1.jpg"));
        libros.add(new Book("Libro 2", "Autor 2", "https://example.com/libro2.jpg"));

        // Configura el adaptador de libros
        bookAdapter = new BookAdapter(this, libros);
        recyclerView.setAdapter(bookAdapter);

        bookAdapter.setOnItemClickListener(position -> {
            Book libroSeleccionado = libros.get(position);
            Toast.makeText(this, "Clic en libro: " + libroSeleccionado.getTitulo(), Toast.LENGTH_SHORT).show();
        });
    }

    private void mostrarListas() {
        // Ejemplo de listas de colecciones
        List<Listas> listas = new ArrayList<>();
        listas.add(new Listas(1, "pruebas"));
        listas.add(new Listas(2, "pruebas 2"));

        // Configura el adaptador de listas
        listasAdapter = new ListasAdapter(this, listas);
        recyclerView.setAdapter(listasAdapter);

        listasAdapter.setOnItemClickListener(position -> {
            Listas listaSeleccionada = listas.get(position);
            Toast.makeText(this, "Clic en lista: " + listaSeleccionada.getNameList(), Toast.LENGTH_SHORT).show();
        });
    }
}