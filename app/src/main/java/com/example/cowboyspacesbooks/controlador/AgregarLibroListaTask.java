package com.example.cowboyspacesbooks.controlador;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AgregarLibroListaTask extends AsyncTask<String, Void, String> {
    private final Context context;

    public AgregarLibroListaTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        String listaId = strings[0];
        String isbn = strings[1];

        try{
            Log.d("Log", "Ingreso a la tarea de agregar libro a lista");
            URL url = new URL("http://192.168.100.5/cowboyspacesbooks/listas/agregarLibroLista.php");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type","application/json");

            JSONObject jsonParam=new JSONObject();
            jsonParam.put("lista",Integer.parseInt(listaId));
            jsonParam.put("isbn", Integer.parseInt(isbn));

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

            return response.toString();
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
                    // Mostrar un Log notificando que el libro se registró correctamente
                    Log.d("AgregarLibroListaTask", "Libro agregado a la lista correctamente");
                } else {
                    String error = jsonObject.getString("message");
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                }
            }catch (JSONException e){
                e.printStackTrace();
            }
        } else {
            // Manejar el caso donde no se obtiene respuesta del servidor
            Log.d("AgregarLibroListaTask", "Error de conexion al servidor");
        }
    }
}
