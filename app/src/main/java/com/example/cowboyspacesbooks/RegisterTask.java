package com.example.cowboyspacesbooks;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.cowboyspacesbooks.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.HttpURLConnection;

public class RegisterTask extends AsyncTask<String, Void, String> {
    private final Context context;

    public RegisterTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
        String numeroCedula = params[0];
        String nombre = params[1];
        String apellido = params[2];
        String email = params[3];
        String contrasena = params[4];
        try {
            Log.d("Log", "Ingreso a la tarea de registro");
            URL url = new URL("http://192.168.100.5/cowboyspacesbooks/index.php");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type","application/json");

            //String postData = "nombre=" + nombre + "&apellido="+ apellido + "&id=" + numeroCedula + "&email=" + email + "&clave=" + contrasena;
            JSONObject jsonParam=new JSONObject();
            jsonParam.put("id",Integer.parseInt(numeroCedula));
            jsonParam.put("nombre", nombre);
            jsonParam.put("apellido",apellido);
            jsonParam.put("email",email);
            jsonParam.put("contrasena",contrasena);

            OutputStream os = conn.getOutputStream();
            os.write(jsonParam.toString().getBytes("UTF-8"));
            os.flush();
            os.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return response.toString();  // Retorna la respuesta del servidor (ej. un JSON)
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    protected void onPostExecute(String result) {
        // Procesar el resultado de la tarea en el hilo principal
        super.onPostExecute(result);
        if (result != null) {
            try {
                JSONObject jsonObject =new JSONObject(result);
                String status = jsonObject.getString("status");
                // Verificar si el registro fue exitoso
                if (status.equals("success")){
                    Intent intent = new Intent(context, MainActivity.class);
                    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    Toast.makeText(context, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
                    // Mostrar un Log notificando que el usuario se registró correctamente// Mostrar un Log notificando que el usuario se registró correctamente
                    Log.d("RegisterTask", "Usuario creado correctamente");
                } else {
                    String error = jsonObject.getString("message");
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                }
            }catch (JSONException e){
                e.printStackTrace();
            }
        } else {
            // Manejar el caso donde no se obtiene respuesta del servidor
            Log.d("RegisterTask", "Error de conexion al servidor");
        }
    }
}
