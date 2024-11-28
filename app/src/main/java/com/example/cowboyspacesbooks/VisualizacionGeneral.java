package com.example.cowboyspacesbooks;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cowboyspacesbooks.controlador.ApiService;
import com.example.cowboyspacesbooks.controlador.RetrofitClient;
import com.example.cowboyspacesbooks.modelo.Book;
import com.example.cowboyspacesbooks.modelo.LibroLista;
import com.example.cowboyspacesbooks.modelo.Listas;
import com.example.cowboyspacesbooks.vista.AgregarLibro;
import com.example.cowboyspacesbooks.vista.AgregarListaBottomSheet;
import com.example.cowboyspacesbooks.vista.VistaPrevia;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VisualizacionGeneral extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ListasAdapter listasAdapter;
    private BookAdapter bookAdapter;

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
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        String listaId = getIntent().getStringExtra("listaId");
        // Obtén el contexto del Intent
        String contexto = getIntent().getStringExtra("contexto");

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
        ImageButton btnBack =findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Cierra la actividad actual y vuelve a la anterior
            }
        });

    }

    private void mostrarLibros() {
        ApiService bookApi = RetrofitClient.getClient().create(ApiService.class);
        String listaId = getIntent().getStringExtra("listaId");
        Log.d("VisualizacionGeneral",listaId);
        // Verifica si listaId es nulo o vacío
        if (listaId != null && !listaId.isEmpty()) {
            // Si hay un listaId, obtener los libros relacionados con la lista
            bookApi.obtenerLibrosListas(listaId).enqueue(new Callback<List<LibroLista>>() {
                @Override
                public void onResponse(Call<List<LibroLista>> call, Response<List<LibroLista>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        List<LibroLista> libroDeLibroListas = response.body();
                        List<Book> listaDeLibros = new ArrayList<>();

                        // Convertir los objetos LibroLista a Book
                        for (LibroLista libroLista : libroDeLibroListas) {
                            Book book = new Book(
                                    libroLista.getTitulo(),
                                    libroLista.getAutor(),
                                    libroLista.getIsbn(),
                                    libroLista.getPortada()
                            );
                            listaDeLibros.add(book);
                        }

                        // Configurar el adaptador con los objetos Book
                        configurarAdaptador(listaDeLibros);
                    } else {
                        Log.d("API_Response", "Código de respuesta: " + response.code());
                        Log.d("API_Response", "Error del cuerpo: " + response.errorBody());
                        Toast.makeText(VisualizacionGeneral.this, "Error al cargar los datos", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<LibroLista>> call, Throwable t) {
                    Log.e("VisualizacionGeneral", "Error al conectar con el servidor", t);
                    Toast.makeText(VisualizacionGeneral.this, "Error al conectar con el servidor", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            // Si no hay listaId, obtener todos los libros
            bookApi.obtenerLibros().enqueue(new Callback<List<Book>>() {
                @Override
                public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        List<Book> listaDeLibros = response.body();

                        // Configurar el adaptador con los libros
                        configurarAdaptador(listaDeLibros);
                    } else {
                        Toast.makeText(VisualizacionGeneral.this, "Error al cargar los datos", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Book>> call, Throwable t) {
                    Log.e("VisualizacionGeneral", "Error al conectar con el servidor", t);
                    Toast.makeText(VisualizacionGeneral.this, "Error al conectar con el servidor", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    // Método para configurar el adaptador
    private void configurarAdaptador(List<Book> listaDeLibros) {
        bookAdapter = new BookAdapter(VisualizacionGeneral.this, listaDeLibros);
        recyclerView.setAdapter(bookAdapter);

        // Configurar el listener para clics en los ítems
        bookAdapter.setOnItemClickListener(new BookAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Book clickedBook = listaDeLibros.get(position);

                // Crear el Intent para la nueva actividad
                Intent intent = new Intent(VisualizacionGeneral.this, VistaPrevia.class);
                intent.putExtra("titulo", clickedBook.getTitulo());
                intent.putExtra("imagenUrl", clickedBook.getCoverImageUrl());
                intent.putExtra("isbn", clickedBook.getIsbn());
                intent.putExtra("contexto", "pruebas");

                // Iniciar la nueva actividad
                startActivity(intent);
            }
        });
    }

    private void mostrarListas() {
        // Ejemplo de listas de colecciones
        ApiService lista = RetrofitClient.getClient().create(ApiService.class);
        lista.obtenerListas().enqueue(new Callback<List<Listas>>() {
            @Override
            public void onResponse(Call<List<Listas>> call, Response<List<Listas>> response) {
                if (response.isSuccessful() && response.body() !=null){
                    List<Listas> listaColeccion = response.body();
                    listasAdapter = new ListasAdapter(VisualizacionGeneral.this,listaColeccion);
                    recyclerView.setAdapter(listasAdapter);
                    listasAdapter.setOnItemClickListener(new ListasAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            Listas clickedLista = listaColeccion.get(position);
                            String listaId = String.valueOf(clickedLista.getLista_Id());
                            Intent intent = new Intent(VisualizacionGeneral.this, VisualizacionGeneral.class);
                            intent.putExtra("contexto","libros");
                            intent.putExtra("listaId",listaId);
                            startActivity(intent);
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<List<Listas>> call, Throwable t) {
                Log.d("onFailuraVisualizacionGeneral", "Error en la consulta");
            }
        });
    }
}