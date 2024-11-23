package com.example.cowboyspacesbooks.vista;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
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

import com.bumptech.glide.Glide;
import com.example.cowboyspacesbooks.R;
import com.example.cowboyspacesbooks.controlador.AgregarLibroListaTask;
import com.example.cowboyspacesbooks.controlador.InsertarLibroTask;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

public class AgregarLibro extends AppCompatActivity {
    private String imageUrl = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_agregar_libro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            //Añadir URL de la imagen
            ImageView bookImage = findViewById(R.id.iv_book_image);
            bookImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Crear un EditText para ingresar la URL
                    final EditText inputUrl = new EditText(AgregarLibro.this);

                    inputUrl.setHint("Ingrese la URL de la imagen");

                    // Crear el AlertDialog
                    new AlertDialog.Builder(AgregarLibro.this)
                            .setTitle("Cambiar Imagen")
                            .setMessage("Ingrese la URL de la imagen")
                            .setView(inputUrl)
                            .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    imageUrl = inputUrl.getText().toString().trim();
                                    if (!imageUrl.isEmpty()) {
                                        //Toast.makeText(AgregarLibro.this, "URL ingresada: " + imageUrl, Toast.LENGTH_SHORT).show();
                                        // Aquí puedes usar una librería como Glide o Picasso para cargar la imagen
                                        Glide.with(AgregarLibro.this).load(imageUrl).into(bookImage);
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
            EditText etTitulo = findViewById(R.id.et_title);
            EditText etAutor = findViewById(R.id.et_author);
            EditText etEditor = findViewById(R.id.et_editor);
            EditText etIsbn = findViewById(R.id.et_isbn);
            ChipGroup chipEstado = findViewById(R.id.chip_group_status);
            EditText etNumPaginas = findViewById(R.id.et_num_pages);
            EditText etDescripcion = findViewById(R.id.et_description);
            ChipGroup chipGroupBookType = findViewById(R.id.chip_group_book_type);
            ChipGroup chipAddWishList = findViewById(R.id.chip_group_wishlist);
            // Configurar el OnClickListener
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String titulo = etTitulo.getText().toString().trim();
                    String autor = etAutor.getText().toString().trim();
                    String editor = etEditor.getText().toString().trim();
                    String isbn = etIsbn.getText().toString().trim();
                    String numPaginas = etNumPaginas.getText().toString().trim();
                    String descripcion = etDescripcion.getText().toString().trim();

                    // Capturar el chip seleccionado
                    int selectedChipWishId = chipAddWishList.getCheckedChipId();
                    String addWishList = "";

                    int selectedChipEstado = chipEstado.getCheckedChipId();
                    String estado = "";

                    int selectedChipId = chipGroupBookType.getCheckedChipId();
                    String bookType = "";

                    if (isbn !=""){
                        if (selectedChipId != View.NO_ID) {
                            if (!imageUrl.isEmpty()) {
                                Chip selectedChip = chipGroupBookType.findViewById(selectedChipId);
                                bookType = selectedChip.getText().toString();
                                Chip selectedWishChip = chipAddWishList.findViewById(selectedChipWishId);
                                addWishList =selectedWishChip.getText().toString();
                                Chip selectedChipStatus = chipEstado.findViewById(selectedChipEstado);
                                estado = selectedChipStatus.getText().toString();

                                //Llamar nueva tarea para la insercion a la base de datos
                                new InsertarLibroTask(AgregarLibro.this).execute(titulo,autor,editor,isbn,numPaginas,descripcion,bookType,imageUrl,estado);
                                //Toast.makeText(AgregarLibro.this, "URL guardada: " + imageUrl, Toast.LENGTH_SHORT).show();

                                if (addWishList.equals("Sí")){
                                    Log.d("AgregarLibro","Entro al if para la creacion de la nuneva tarea");
                                    new AgregarLibroListaTask(AgregarLibro.this).execute("7",isbn);
                                }
                                finish();
                            } else {
                                Toast.makeText(AgregarLibro.this, "No se ha ingresado una URL", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            bookType = "No seleccionado"; // En caso de que no se haya seleccionado ningún chip
                        }
                    } else {
                        Toast.makeText(AgregarLibro.this, "No ha ingresado ISBN", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            // Referencia al botón btn_back
            ImageButton btnBack = findViewById(R.id.btn_back);
            // Configurar el OnClickListener para devolver a la página anterior
            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish(); // Cierra la actividad actual y vuelve a la anterior
                }
            });
            return insets;
        });
    }
}