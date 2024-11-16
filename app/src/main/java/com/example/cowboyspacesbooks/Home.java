package com.example.cowboyspacesbooks;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.view.View;
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

            BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    if (item.getItemId() == R.id.nav_home) {
                        // Ya estás en la actividad principal
                        return true;
                    } else if (item.getItemId() == R.id.nav_memorizar) {
                        startActivity(new Intent(Home.this, Memorizar.class));
                        return true;
                    } else if (item.getItemId() == R.id.nav_logros) {
                        startActivity(new Intent(Home.this, Logros.class));
                        return true;
                    } else if(item.getItemId() == R.id.nav_profile){
                        return true;
                    }
                    return false;
                }
            });
            return insets;
        });
    }
    public void onCardClick(View view) {
        startActivity(new Intent(Home.this, AgregarLibro.class));
    }
}