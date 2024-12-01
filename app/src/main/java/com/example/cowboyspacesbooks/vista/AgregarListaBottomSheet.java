package com.example.cowboyspacesbooks.vista;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.cowboyspacesbooks.R;
import com.example.cowboyspacesbooks.controlador.ApiService;
import com.example.cowboyspacesbooks.controlador.RetrofitClient;
import com.example.cowboyspacesbooks.modelo.Listas;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgregarListaBottomSheet extends BottomSheetDialogFragment {

    private AddListaListener listener;

    public interface AddListaListener {
        void onListaAdded(String collectionName);
    }

    public void setAddCollectionListener(AddListaListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_bottom_sheet_agregar_lista, container, false);

        EditText etCollectionName = view.findViewById(R.id.et_lista_name);
        Button btnConfirm = view.findViewById(R.id.btn_confirmar_add_lista);

        btnConfirm.setOnClickListener(v -> {
            String collectionName = etCollectionName.getText().toString().trim();
            if (!collectionName.isEmpty()) {
                // Procede con la lógica para insertar la lista
                Listas lista = new Listas(collectionName);
                ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
                apiService.insertarLista(lista).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Log.d("AgregarListaBottomSheet", "Lista insertada correctamente");
                            if (listener != null) {
                                listener.onListaAdded(collectionName);
                            }
                            dismiss(); // Cerrar el diálogo solo si la operación fue exitosa
                        } else {
                            Log.d("AgregarListaBottomSheet", "Error al insertar la lista");
                            Toast.makeText(getContext(), "Error al insertar la lista.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.d("AgregarListaBottomSheet", "Lista no insertada: " + t.getMessage());
                        Toast.makeText(getContext(), "Error al conectar con el servidor.", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(getContext(), "Por favor, ingresa un nombre para la lista.", Toast.LENGTH_SHORT).show();
            }

        });
        return view;
    }
}
