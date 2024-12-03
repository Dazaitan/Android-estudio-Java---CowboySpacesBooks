package com.example.cowboyspacesbooks.vista;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cowboyspacesbooks.NotasAdapter;
import com.example.cowboyspacesbooks.R;
import com.example.cowboyspacesbooks.controlador.ApiService;
import com.example.cowboyspacesbooks.controlador.RetrofitClient;
import com.example.cowboyspacesbooks.modelo.Notes;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotasLibro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_notas);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            ImageButton btnBack = findViewById(R.id.btn_back);

            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish(); // Cierra la actividad actual y vuelve a la anterior
                }
            });
            return insets;
        });
        cargarNotas(getIntent().getStringExtra("isbn"));
    }
    private void cargarNotas(String isbn){
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        RecyclerView recyclerView = findViewById(R.id.recycler_view_notas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        apiService.obtenerNotasporLibro(isbn).enqueue(new Callback<List<Notes>>() {
            @Override
            public void onResponse(Call<List<Notes>> call, Response<List<Notes>> response) {
                List<Notes> notas = response.body();
                NotasAdapter adapter = new NotasAdapter(NotasLibro.this, notas);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Notes>> call, Throwable t) {

            }
        });
    }
}