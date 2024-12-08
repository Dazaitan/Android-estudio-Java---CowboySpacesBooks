package com.example.cowboyspacesbooks.vista;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.cowboyspacesbooks.R;
import com.example.cowboyspacesbooks.SelectorColecciones;
import com.example.cowboyspacesbooks.controlador.AgregarLibroListaTask;
import com.example.cowboyspacesbooks.controlador.ApiService;
import com.example.cowboyspacesbooks.controlador.InsertarLibroTask;
import com.example.cowboyspacesbooks.controlador.RetrofitClient;
import com.example.cowboyspacesbooks.modelo.Book;
import com.example.cowboyspacesbooks.modelo.LibroLista;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgregarLibro extends AppCompatActivity {
    ImageButton btnSave;
    EditText etTitulo,etAutor,etPagsLeidas,etEditor,etIsbn, etNumPaginas,etDescripcion;
    ChipGroup chipAddWishList,chipGroupBookType,chipEstado;
    private String imageUrl = "";
    private String contexto;
    private String isbn, isbn2;
    private static final int REQUEST_CODE_SELECCIONAR_COLECCIONES = 1001;
    private ArrayList<Integer> listasSeleccionadas = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_agregar_libro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            contexto = getIntent().getStringExtra("contexto");
            Log.d("AgregarLibro", contexto);
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
                                        // Aquí para cargar la imagen
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

            boolean datosCargados = false; // Bandera para controlar la recarga de datos
            if (contexto.equals("editar") && !datosCargados) {
                isbn2 = getIntent().getStringExtra("isbn");
                TextView tvtitulo = findViewById(R.id.tv_title);
                tvtitulo.setText("Editar libro");
                cargarDetallesLibroAPI(isbn2);
                Log.d("AgregarLibro", "Ingreso al contexto de editar");
                datosCargados = true; // Marcar los datos como cargados
            }
            //Remitir insercion de libro
            btnSave = findViewById(R.id.btn_save);
            etTitulo = findViewById(R.id.et_title);
            etAutor = findViewById(R.id.et_author);
            etPagsLeidas = findViewById(R.id.et_pages_read);
            etEditor = findViewById(R.id.et_editor);
            etIsbn = findViewById(R.id.et_isbn);
            chipEstado = findViewById(R.id.chip_group_status);
            etNumPaginas = findViewById(R.id.et_num_pages);
            etDescripcion = findViewById(R.id.et_description);
            chipGroupBookType = findViewById(R.id.chip_group_book_type);
            chipAddWishList = findViewById(R.id.chip_group_wishlist);
            // Configurar el OnClickListener
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String titulo = etTitulo.getText().toString().trim();
                    String autor = etAutor.getText().toString().trim();
                    String editor = etEditor.getText().toString().trim();
                    String pagsLeidas = etPagsLeidas.getText().toString().trim();
                    String isbn = etIsbn.getText().toString().trim();
                    //Hay un error en la aplicacion  que almomento de abri
                    String numPaginas = etNumPaginas.getText().toString().trim();
                    String descripcion = etDescripcion.getText().toString().trim();

                    // Capture el chip seleccionado
                    int selectedChipWishId = chipAddWishList.getCheckedChipId();
                    String addWishList = "";

                    int selectedChipEstado = chipEstado.getCheckedChipId();
                    String estado = "";

                    int selectedChipId = chipGroupBookType.getCheckedChipId();
                    String bookType = "";

                    if (isbn != "" || contexto != "") {
                        if (selectedChipId != View.NO_ID) {
                            if (!imageUrl.isEmpty()) {
                                Log.d("AgregarLibro", contexto);
                                Chip selectedChip = chipGroupBookType.findViewById(selectedChipId);
                                bookType = selectedChip.getText().toString();
                                Chip selectedWishChip = chipAddWishList.findViewById(selectedChipWishId);
                                addWishList = selectedWishChip.getText().toString();
                                Chip selectedChipStatus = chipEstado.findViewById(selectedChipEstado);
                                estado = selectedChipStatus.getText().toString();

                                Book libro = new Book(titulo, editor, autor, bookType, descripcion, estado, isbn, numPaginas, pagsLeidas, imageUrl);
                                if (contexto.equals("insertar")) {
                                    Log.d("Agregar Libro", "ingreso al contexto de insertar");
                                    //Llamar nueva tarea para la insercion a la base de datos
                                    new InsertarLibroTask(AgregarLibro.this).execute(titulo, autor, editor, isbn, numPaginas, descripcion, bookType, imageUrl, estado);
                                    //Toast.makeText(AgregarLibro.this, "URL guardada: " + imageUrl, Toast.LENGTH_SHORT).show();
                                } else if (contexto.equals("editar")) {
                                    //actualizacion de datos
                                    libro.setIsbnEdit(isbn2);
                                    /*Gson gson = new Gson();
                                    String json = gson.toJson(libro);
                                    Log.d("ActualizarLibroJSON", "Datos enviados: " + json);*/
                                    ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
                                    apiService.actualizarLibro(libro).enqueue(new Callback<Void>() {
                                        @Override
                                        public void onResponse(Call<Void> call, Response<Void> response) {
                                            Log.d("ActualizarLibro", "Actualizo correctaente");
                                        }

                                        @Override
                                        public void onFailure(Call<Void> call, Throwable t) {
                                            Log.d("ActualizarLibro", "Error al actualizar");
                                        }
                                    });
                                }

                                // Enviar datos a cada lista seleccionada
                                if (!listasSeleccionadas.isEmpty()){
                                    for (int listaId : listasSeleccionadas) {
                                        insertarRelListasLibros(listaId, isbn);
                                    }
                                }

                                if (addWishList.equals("Sí")) {
                                    Log.d("AgregarLibro", "Entro al if para la creacion de la nuneva tarea");
                                    new AgregarLibroListaTask(AgregarLibro.this).execute("7", isbn);
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

            TextView seleccionarColecciones = findViewById(R.id.tv_SeleccionarColeccion);
            seleccionarColecciones.setOnClickListener(view -> {
                Intent intent = new Intent(AgregarLibro.this, SelectorColecciones.class);
                startActivityForResult(intent, REQUEST_CODE_SELECCIONAR_COLECCIONES);
            });
            return insets;
        });
    }
    private void cargarDetallesLibroAPI(String isbn) {
        ApiService bookApi = RetrofitClient.getClient().create(ApiService.class);
        bookApi.obtenerDetallesLibros(isbn).enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) { //realizar pruebas
                if (response.isSuccessful() && response.body() != null) {
                    List<Book> listaDeLibros = response.body();
                    Book libro = listaDeLibros.get(0);
                    if (etTitulo.getText().toString().isEmpty()) {
                        etTitulo.setText(libro.getTitulo());
                    }
                    if (etIsbn.getText().toString().isEmpty()) {
                        etIsbn.setText(libro.getIsbn());
                    }
                    if (etAutor.getText().toString().isEmpty()) {
                        etAutor.setText(libro.getAutor());
                    }
                    if (etEditor.getText().toString().isEmpty()) {
                        etEditor.setText(libro.getEditorial());
                    }
                    if (etNumPaginas.getText().toString().isEmpty()) {
                        etNumPaginas.setText(libro.getnPaginas());
                    }
                    if (etDescripcion.getText().toString().isEmpty()) {
                        etDescripcion.setText(libro.getDescripcion());
                    }
                    // Configuración de chips y estado
                    String estado = libro.getEstado();
                    if (estado != null) {
                        if (estado.equals("Leyendo")) {
                            chipEstado.check(R.id.chip_read_later);
                        } else if (estado.equals("Quiero leer")) {
                            chipEstado.check(R.id.chip_read_now);
                        } else if (estado.equals("Leido")) {
                            chipEstado.check(R.id.chip_finished);
                        } else if (estado.equals("Dropeado")) {
                            chipEstado.check(R.id.chip_give_up);
                        }
                    }

                    String formato = libro.getFormato();
                    if (formato != null) {
                        if (formato.equals("Tapa dura")) {
                            chipGroupBookType.check(R.id.chip_hardpaper_book);
                        } else if (formato.equals("Tapa blanda")) {
                            chipGroupBookType.check(R.id.chip_softpaper_book);
                        } else if (formato.equals("libro electronico")) {
                            chipGroupBookType.check(R.id.chip_ebook);
                        } else if (formato.equals("Audiolibro")) {
                            chipGroupBookType.check(R.id.chip_audio_book);
                        }
                    }
                    imageUrl = libro.getCoverImageUrl();
                    ImageView imagenImageView = findViewById(R.id.iv_book_image);
                    if (libro.getCoverImageUrl() != null) {
                        Glide.with(AgregarLibro.this)
                                .load(libro.getCoverImageUrl())
                                .placeholder(R.drawable.ic_book_placeholder)
                                .error(R.drawable.error_image)
                                .into(imagenImageView);
                    } else {
                        Log.d("AgregarLibro", "No hay foto para cargar " + libro.getCoverImageUrl());
                    }
                } else {
                    Toast.makeText(AgregarLibro.this, "Error al cargar los datos", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                Log.e("Home", "Error al conectar con el servidor", t);
                Toast.makeText(AgregarLibro.this, "Error al conectar con el servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SELECCIONAR_COLECCIONES && resultCode == RESULT_OK && data != null) {
            listasSeleccionadas = data.getIntegerArrayListExtra("idsSeleccionados");
            if (listasSeleccionadas != null) {
                for (int id : listasSeleccionadas) {
                    Log.d("AgregarLibro", "ID de lista seleccionada: " + id);
                }
            }
        }
    }
    private void insertarRelListasLibros(int listaId, String isbn) {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

        // Llamada al endpoint para enviar el ID de la lista y el ISBN
        apiService.insertarLibroEnLista(listaId, isbn).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("AgregarLibro", "Datos enviados: Lista ID = " + listaId + ", ISBN = " + isbn);
                } else {
                    Log.e("AgregarLibro", "Error en la respuesta: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("AgregarLibro", "Error al conectar con el servidor.", t);
            }
        });
    }


}