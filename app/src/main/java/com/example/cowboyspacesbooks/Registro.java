package com.example.cowboyspacesbooks;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cowboyspacesbooks.controlador.RegisterTask;

public class Registro extends AppCompatActivity {
    private EditText txtNombre,txtApellido,txtEmail,txtContrasena,txtCedula,txtContrasenaConfirmar;
    private Button btnRegistro;
    UserDAO userDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        txtNombre=findViewById(R.id.txtNombreRegistro);
        txtApellido=findViewById(R.id.txtApellidoRegistro);
        txtEmail=findViewById(R.id.txtEmailRegistro);
        txtContrasena=findViewById(R.id.txtPasswordRegistro);
        txtContrasenaConfirmar=findViewById(R.id.txtPasswordRegistro2);
        txtCedula=findViewById(R.id.txtIdUsuario);
        btnRegistro=findViewById(R.id.registrarUsuario);
    }
    public void registrarUsuario(View view){
        userDao =new UserDAO(this);
        String nombre =txtNombre.getText().toString();
        String apellido =txtApellido.getText().toString();
        String numeroCedula =txtCedula.getText().toString();
        String contrasena =txtContrasena.getText().toString();
        String contrasenaConfirmar =txtContrasenaConfirmar.getText().toString();
        String email =txtEmail.getText().toString();

        if (contrasena.equals(contrasenaConfirmar)){
            new RegisterTask(Registro.this).execute(numeroCedula,nombre,apellido,email,contrasena);
        } else {
            Toast.makeText(Registro.this,"Error al confirmar la contraseña",Toast.LENGTH_SHORT).show();
        }
    }
}