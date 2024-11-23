package com.example.cowboyspacesbooks.controlador;

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
import java.net.HttpURLConnection;
import java.net.URL;

public class InsertarLibroTask extends AsyncTask<String, Void, String> {
    private final Context context;

    public InsertarLibroTask(Context context) {
        this.context = context;
    }
    @Override
    protected String doInBackground(String... strings) {
        String titulo = strings[0];
        String autor = strings[1];
        String editor = strings[2];
        String isbn = strings[3];
        String numPaginas = strings[4];
        String descripcion = strings[5];
        String bookType = strings[6];
        String portada = strings[7];
        String estado = strings[8];
        try {
            Log.d("Log", "Ingreso a la tarea de Insertar nuevo libro");
            URL url = new URL("http://192.168.100.5/cowboyspacesbooks/libros/insertarLibro.php");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type","application/json");

            JSONObject jsonParam=new JSONObject();
            jsonParam.put("isbn",Integer.parseInt(isbn));
            jsonParam.put("titulo", titulo);
            jsonParam.put("autor",autor);
            jsonParam.put("editorial",editor);
            jsonParam.put("numPaginas",Integer.parseInt(numPaginas));
            jsonParam.put("descripcion",descripcion);
            jsonParam.put("Formato",bookType);
            jsonParam.put("portada",portada);
            jsonParam.put("estado",estado);

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
        }catch (Exception e){
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
                    Toast.makeText(context, "Libro registrado correctamente", Toast.LENGTH_SHORT).show();
                    // Mostrar un Log notificando que el libro se registró correctamente
                    Log.d("InsertLibroTask", "Libro creado correctamente");
                } else {
                    String error = jsonObject.getString("message");
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                }
            }catch (JSONException e){
                e.printStackTrace();
            }
        } else {
            // Manejar el caso donde no se obtiene respuesta del servidor
            Log.d("InsertLibroTask", "Error de conexion al servidor");
        }
    }
}
