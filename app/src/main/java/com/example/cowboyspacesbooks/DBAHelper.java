package com.example.cowboyspacesbooks;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBAHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME ="cowboySpacesBooks";
    private static final int DATABASE_VERSION=1;

    public DBAHelper(Context context) {
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String crearBase= "CREATE TABLE Persona(" +
                "id integer primary key autoincrement," +
                "nombre TEXT," +
                "apellido TEXT," +
                "email TEXT," +
                "celular TEXT," +
                "contrasena TEXT" +
                ")";
        db.execSQL(crearBase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP table IF EXISTS Persona");
        onCreate(db);
    }

}
