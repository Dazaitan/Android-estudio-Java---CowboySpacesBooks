package com.example.cowboyspacesbooks.vista;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.cowboyspacesbooks.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ModoLectura extends AppCompatActivity {
    private TextView timerTextView;
    private ImageButton pauseButton;
    private boolean isPaused = false;
    private Handler handler = new Handler();
    private long startTime = 0;
    private long timeWhenPaused = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_modo_lectura);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);


            FloatingActionButton btnAddNote = findViewById(R.id.btn_add_note);
            btnAddNote = findViewById(R.id.btn_add_note);
            btnAddNote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(ModoLectura.this, GestionNotasLayout.class));
                }
            });

            pauseButton = findViewById(R.id.pause_button);
            timerTextView = findViewById(R.id.timer_text);
            // Configurar el evento OnClickListener para intercalar imagenes cuando se presione el boton de pausar y play
            pauseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isPaused) {
                        // Reanudar el cronómetro
                        startTime = System.currentTimeMillis() - timeWhenPaused;
                        handler.postDelayed(updateTimerThread, 0);
                        pauseButton.setImageResource(R.drawable.ic_pause); // Cambiar ícono a pausa
                    } else {
                        // Pausar el cronómetro
                        timeWhenPaused = System.currentTimeMillis() - startTime;
                        handler.removeCallbacks(updateTimerThread);
                        pauseButton.setImageResource(R.drawable.ic_play); // Cambiar ícono a reproducir
                    }
                    isPaused = !isPaused;
                }
            });
            startTime = System.currentTimeMillis();
            handler.postDelayed(updateTimerThread, 0);

            //Captura de datos del intent donde se desencadena el evento
            TextView bookTitle = findViewById(R.id.book_title);
            ImageView bookCover = findViewById(R.id.book_cover);
            // Recibir los datos del Intent y cargarlos en la interfaz
            Intent intent = getIntent();
            if (intent != null) {
                String titulo = intent.getStringExtra("titulo");
                String imagenUrl = intent.getStringExtra("imagenUrl");

                // Configurar los datos en las vistas
                if (titulo != null) {
                    bookTitle.setText(titulo);
                }

                if (imagenUrl != null) {
                    Glide.with(this)
                            .load(imagenUrl)
                            .placeholder(R.drawable.ic_book_placeholder)
                            .error(R.drawable.error_image)
                            .into(bookCover);
                }
            }

            //Configuracion de confirmacion de vista
            ImageButton btnConfirmar = findViewById(R.id.confirm_button);
            btnConfirmar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            ImageButton btnBack = findViewById(R.id.cancel_button);
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
    private Runnable updateTimerThread = new Runnable() {
        public void run() {
            long currentTime = System.currentTimeMillis();
            long timeInMillis = currentTime - startTime;
            int seconds = (int) (timeInMillis / 1000);
            int minutes = seconds / 60;
            int hours = minutes / 60;
            minutes = minutes % 60;
            seconds = seconds % 60;

            timerTextView.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
            handler.postDelayed(this, 1000);
        }
    };
    protected void onDestroy() {
        super.onDestroy();
        // Detener el Runnable para evitar fugas de memoria
        handler.removeCallbacks(updateTimerThread);
    }
}