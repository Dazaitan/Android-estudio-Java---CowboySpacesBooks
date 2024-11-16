package com.example.cowboyspacesbooks;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AgregarLibro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_agregar_libro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            ImageView bookImage = findViewById(R.id.iv_book_image);
            bookImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Crear un EditText para ingresar la URL
                    EditText inputUrl = new EditText(AgregarLibro.this);
                    inputUrl.setHint("Ingrese la URL de la imagen");

                    // Crear el AlertDialog
                    new AlertDialog.Builder(AgregarLibro.this)
                            .setTitle("Cambiar Imagen")
                            .setMessage("Ingrese la URL de la imagen")
                            .setView(inputUrl)
                            .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String url = inputUrl.getText().toString().trim();
                                    if (!url.isEmpty()) {
                                        // Lógica para cargar la imagen desde la URL
                                        Toast.makeText(AgregarLibro.this, "URL ingresada: " + url, Toast.LENGTH_SHORT).show();
                                        // Aquí puedes usar una librería como Glide o Picasso para cargar la imagen
                                    } else {
                                        Toast.makeText(AgregarLibro.this, "Por favor, ingrese una URL válida", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            })
                            .setNegativeButton("Cancelar", null)
                            .show();
                }
            });


            //Remitir insercion de libro
            ImageButton btnSave = findViewById(R.id.btn_save);

            // Configurar el OnClickListener
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Acción que quieres ejecutar cuando se haga clic en el botón
                    Toast.makeText(AgregarLibro.this, "Botón Guardar clickeado", Toast.LENGTH_SHORT).show();
                    // Aquí puedes agregar la lógica para guardar los datos o realizar otra acción
                }
            });
            return insets;
        });
    }
}