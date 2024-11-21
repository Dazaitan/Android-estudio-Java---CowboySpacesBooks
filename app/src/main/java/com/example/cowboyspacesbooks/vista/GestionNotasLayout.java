package com.example.cowboyspacesbooks.vista;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cowboyspacesbooks.R;
import com.example.cowboyspacesbooks.vista.BarraInferiorGestionNotas;

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

    @Override
    public void onOptionSelected(String option, int imageResId) {
        btnMenu.setText(option);
        Drawable drawable = ContextCompat.getDrawable(this, imageResId);
        btnMenu.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        Toast.makeText(this, "Seleccionaste: " + option, Toast.LENGTH_SHORT).show();
    }
}