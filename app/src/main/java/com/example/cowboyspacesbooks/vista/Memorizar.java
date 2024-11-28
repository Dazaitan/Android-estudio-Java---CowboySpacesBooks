package com.example.cowboyspacesbooks.vista;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.annotation.NonNull;

import com.example.cowboyspacesbooks.Home;
import com.example.cowboyspacesbooks.R;
import com.example.cowboyspacesbooks.VisualizacionGeneral;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Memorizar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_memorizar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            //Barra de navegacion
            BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    if (item.getItemId() == R.id.nav_home) {
                        startActivity(new Intent(Memorizar.this, Home.class));
                        return true;
                    } else if (item.getItemId() == R.id.nav_memorizar) {
                        //Estoy en esta clase
                        return true;
                    } else if (item.getItemId() == R.id.nav_logros) {
                        startActivity(new Intent(Memorizar.this, Logros.class));
                        return true;
                    } else if(item.getItemId() == R.id.nav_coleccion){
                        Intent intent = new Intent(Memorizar.this, VisualizacionGeneral.class);
                        intent.putExtra("contexto","listas");
                        startActivity(intent);
                        return true;
                    }
                    return false;
                }
            });
            return insets;
        });
    }
}