package com.example.cowboyspacesbooks;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Login extends AppCompatActivity {
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button btn;
    private UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        editTextEmail=findViewById(R.id.txtEmail);
        editTextPassword=findViewById(R.id.editTextTextPassword);
        btn=findViewById(R.id.inicio_sesionBtn);

    }
    public void iniciar_Sesion(View view){
        String email = editTextEmail.getText().toString();
        String contrasena = editTextPassword.getText().toString();
        userDAO=new UserDAO(this);
        userDAO.login(email,contrasena, new IloginCallback(){
            @Override
            public void onLoginSuccess() {
                Intent intent = new Intent(Login.this, Home.class);
                startActivity(intent);
            }
            @Override
            public void onLoginFailure() {
                Toast.makeText(Login.this, "El usuario no existe", Toast.LENGTH_LONG).show();
            }
        });

    }

}