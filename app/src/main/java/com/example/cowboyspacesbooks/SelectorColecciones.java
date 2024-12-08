package com.example.cowboyspacesbooks;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cowboyspacesbooks.controlador.ApiService;
import com.example.cowboyspacesbooks.controlador.RetrofitClient;
import com.example.cowboyspacesbooks.modelo.Listas;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectorColecciones extends AppCompatActivity {
    private RecyclerView recyclerView;
    GridLayoutManager gridLayoutManager;
    private ListasAdapter listasAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_selector_colecciones);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.prueba), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ImageButton btnBack =findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Cierra la actividad actual y vuelve a la anterior
            }
        });
        ImageButton btnSave =findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Listas> listasSeleccionadas = listasAdapter.getSelectedListas();
                Intent resultIntent = new Intent();

                ArrayList<Integer> idsSeleccionados = new ArrayList<>();
                for (Listas lista : listasSeleccionadas) {
                    Log.d("SelectorColecciones", "Lista seleccionada: " + lista.getNameList() + " id " + lista.getLista_Id());
                    idsSeleccionados.add(lista.getLista_Id());
                }

                resultIntent.putIntegerArrayListExtra("idsSeleccionados", idsSeleccionados);
                setResult(RESULT_OK, resultIntent);
                finish(); // Cierra la actividad actual y vuelve a la anterior
            }
        });
        recyclerView = findViewById(R.id.rv_collections);
        gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        mostrarListas();
    }
    private void mostrarListas() {
        ApiService lista = RetrofitClient.getClient().create(ApiService.class);
        lista.obtenerListas().enqueue(new Callback<List<Listas>>() {
            @Override
            public void onResponse(Call<List<Listas>> call, Response<List<Listas>> response) {
                if (response.isSuccessful() && response.body() !=null){
                    List<Listas> listaColeccion = response.body();
                    Log.e("SelectorColecciones", "ingreso a la actividad de cargar las seleccion de coleccion");
                    listasAdapter = new ListasAdapter(SelectorColecciones.this,listaColeccion,true);
                    recyclerView.setAdapter(listasAdapter);
                }
            }
            @Override
            public void onFailure(Call<List<Listas>> call, Throwable t) {
                Log.d("onFailuraVisualizacionGeneral", "Error en la consulta");
            }
        });
    }
}