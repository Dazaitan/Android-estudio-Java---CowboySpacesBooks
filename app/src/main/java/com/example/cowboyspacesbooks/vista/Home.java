package com.example.cowboyspacesbooks.vista;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cowboyspacesbooks.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.Toast;
import androidx.annotation.NonNull;

import android.view.MenuItem;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Configurar el listener de la barra de navegación inferior
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.nav_home) {
                    Toast.makeText(Home.this, "Home seleccionado", Toast.LENGTH_SHORT).show();
                    // Lógica para el fragmento o actividad de Home
                    return true;
                } else if (itemId == R.id.nav_search) {
                    Toast.makeText(Home.this, "Search seleccionado", Toast.LENGTH_SHORT).show();
                    // Lógica para el fragmento o actividad de Search
                    return true;
                } else if (itemId == R.id.nav_profile) {
                    Toast.makeText(Home.this, "Profile seleccionado", Toast.LENGTH_SHORT).show();
                    // Lógica para el fragmento o actividad de Profile
                    return true;
                } else {
                    return false;
                }
            }
        });
    }
}