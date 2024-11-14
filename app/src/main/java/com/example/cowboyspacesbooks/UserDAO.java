package com.example.cowboyspacesbooks;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class UserDAO {
    private DBAHelper dbaHelper;

    public UserDAO(Context context) {
        dbaHelper=new DBAHelper(context);
    }
    public void login(String email, String password, IloginCallback callback){
        new LoginTask(email,password,this,callback).execute();
    }
    public boolean checkLocalDatabase(String email,String password){
        SQLiteDatabase db=dbaHelper.getReadableDatabase();

        String[] columns={"email","contrasena"};
        String condicion="email=? AND contrasena=?";
        String[] atributos ={email, password};
        Cursor cursor =db.query("Persona",columns,condicion,atributos,null,null,null);

        if (cursor.getCount()>0){
            cursor.moveToFirst();
            String storedPassword = password;
            cursor.close();

            if (storedPassword.equals(password)) {
                Log.d("Login", "Acceso permitido desde la base de datos local");
                return true; // Permitir el acceso
            } else {
                // La contraseña local no es correcta
                Log.d("Login", "Contraseña local no es correcta");
                return false; // Denegar el acceso
            }
        } else {
            cursor.close();
            Log.d("Login", "El usuario no existe en la base de datos local");
            return false; // El usuario no existe en la base de datos local
        }
    }
    public long insertUser(String nombre, String apellido, String cedula, String email, String contrasena){
        SQLiteDatabase db = dbaHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("apellido",apellido);
        values.put("email",email);
        values.put("contrasena",contrasena);
        values.put("celular",cedula);
        return db.insert("Persona",null,values);
    };
}
