package com.example.cowboyspacesbooks.vista;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cowboyspacesbooks.R;
import com.example.cowboyspacesbooks.controlador.ApiService;
import com.example.cowboyspacesbooks.controlador.RetrofitClient;
import com.example.cowboyspacesbooks.modelo.Notes;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GestionNotasLayout extends AppCompatActivity implements BarraInferiorGestionNotas.OnOptionSelectedListener {
    private Button btnMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gestion_notas_layout);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            String isbn = getIntent().getStringExtra("isbn");
            EditText pagInicio = findViewById(R.id.edt_pagina_inicio);
            EditText pagFinal = findViewById(R.id.edt_pagina_final);
            EditText cuerpo = findViewById(R.id.edt_note_body);
            //Capturar evento para desplegar el menu inferior
            btnMenu = findViewById(R.id.btn_book_content);
            btnMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Crear una instancia del BottomSheetDialogFragment
                    BarraInferiorGestionNotas bottomSheet = new BarraInferiorGestionNotas();
                    // Mostrar el menú desplegable
                    bottomSheet.show(getSupportFragmentManager(), "BarraInferiorGestionNotas");
                }
            });
            //Boton de confirmar adicion de nota
            ImageButton btnSave = findViewById(R.id.btn_save);

            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnMenu.getText();
                    insertarNota(isbn,btnMenu.getText().toString(),cuerpo.getText().toString(),pagInicio.getText().toString(),pagFinal.getText().toString());
                    Log.d("GestionNotasLayout","Nota guardada correctamente");
                    finish();
                }
            });
            //Boton cerrar
            ImageButton btnClose = findViewById(R.id.btn_close);
            btnClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish(); // Cierra la actividad actual y vuelve a la anterior
                }
            });
            return insets;
        });
    }
    private void insertarNota(String isbn, String tipoNota, String cuerpo, String pagInicio, String pagFinal) {
        ApiService noteApi = RetrofitClient.getClient().create(ApiService.class);
        Notes note = new Notes(isbn,tipoNota,cuerpo,Integer.parseInt(pagInicio),Integer.parseInt(pagFinal));
        Call<Void> call = noteApi.insertarNota(note);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("InsertarLibro", "Libro insertado correctamente");
                    Toast.makeText(getApplicationContext(), "Libro insertado con éxito", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("InsertarLibro", "Error en la inserción: " + response.code());
                    Toast.makeText(getApplicationContext(), "Error al insertar libro", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("InsertarLibro", "Error en la solicitud: " + t.getMessage());
                Toast.makeText(getApplicationContext(), "Error al conectar con el servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onOptionSelected(String option, int imageResId) {
        btnMenu.setText(option);
        Drawable drawable = ContextCompat.getDrawable(this, imageResId);
        btnMenu.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        Toast.makeText(this, "Seleccionaste: " + option, Toast.LENGTH_SHORT).show();
    }
}