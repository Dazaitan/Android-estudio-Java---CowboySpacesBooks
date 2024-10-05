package com.example.cowboyspacesbooks;

import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class LoginTask extends AsyncTask<String, Void, Boolean> {
    private String email;
    private String contrasena;
    private UserDAO userDAO;
    private IloginCallback callback;

    public LoginTask(String email, String contrasena, UserDAO userDAO, IloginCallback callback) {
        this.email = email;
        this.contrasena = contrasena;
        this.userDAO = userDAO;
        this.callback = callback;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        try{
            //Enviar peticion a la API
            String apiResponse = sendDataToAPI(email,contrasena);
            if (apiResponse != null){
                try {
                    JSONObject jsonResponse = new JSONObject(apiResponse);
                    String data = jsonResponse.getString("email");


                    if (data.equals(email)){
                        Log.d("Login","Acceso permitido");
                        return true;
                    }
                }catch (Exception e){
                    throw new RuntimeException(e);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    private String sendDataToAPI(String email, String contrasena){
        try{
            String urlString = "http://192.168.100.5/cowboyspacesbooks/?email=" + URLEncoder.encode(email, "UTF-8") +
                    "&clave=" + URLEncoder.encode(contrasena, "UTF-8");
            Log.d("urlString",urlString);
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Intentar obtener el flujo de entrada
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            //connection.getInputStream(): Este método obtiene el flujo de entrada (input stream) desde la conexión HTTP (connection), que contiene la respuesta de la API o servicio web.
            //InputStreamReader se utiliza para convertir el flujo de entrada a bytes (InputStream) en caracteres
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            Log.d("SendDataToAPI", "Response: " + response.toString());
            return response.toString();
        } catch (IOException e) {
            if (e instanceof java.net.HttpRetryException) {
                Log.d("SendDataToAPI", "HTTP Retry Exception");
            } else if (e instanceof java.net.UnknownHostException) {
                Log.d("SendDataToAPI", "Unknown Host Exception");
            } else if (e instanceof java.net.ConnectException) {
                Log.d("SendDataToAPI", "Connect Exception");
            } else if (e instanceof java.net.SocketTimeoutException) {
                Log.d("SendDataToAPI", "Socket Timeout Exception");
            } else {
                Log.d("SendDataToAPI", "Other IOException: " + e.getMessage());
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("SendDataToAPI", "Error al conectar a la API", e);
            return null; // No se pudo conectar a la API
        }
    }
}
