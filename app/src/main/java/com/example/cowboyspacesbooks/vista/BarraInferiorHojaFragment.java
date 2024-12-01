package com.example.cowboyspacesbooks.vista;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cowboyspacesbooks.R;
import com.example.cowboyspacesbooks.controlador.ApiService;
import com.example.cowboyspacesbooks.controlador.RetrofitClient;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BarraInferiorHojaFragment extends BottomSheetDialogFragment {
    private static final String ARG_PARAM_ISBN = "param";

    public static BarraInferiorHojaFragment nuevaInstancia(String parametro) {
        BarraInferiorHojaFragment fragment = new BarraInferiorHojaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_ISBN, parametro);
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_barra_inferior_gestion_libros, container, false);
        String parametro = getArguments() != null ? getArguments().getString(ARG_PARAM_ISBN) : null;
        // Configura los elementos de la vista
        TextView editOption = view.findViewById(R.id.edit_option);
        TextView deleteOption = view.findViewById(R.id.delete_option);

        editOption.setOnClickListener(v -> {
            // Acción para editar el libro
            if (parametro != ""){
                Intent intent = new Intent(getContext(), AgregarLibro.class);
                intent.putExtra("isbn", parametro);
                intent.putExtra("contexto", "editar");
                startActivity(intent);
                Log.d("BarraInferiorHojaFragment", parametro);
            }
            dismiss(); // Cierra el BottomSheet
        });

        deleteOption.setOnClickListener(v -> {
            if (parametro != null && !parametro.isEmpty()) {
                ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
                apiService.eliminarLibro(parametro).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            if (isAdded()) {
                                Toast.makeText(requireContext(), "Libro eliminado correctamente.", Toast.LENGTH_SHORT).show();
                            }
                            Log.d("EliminarLibro", "Libro eliminado correctamente.");
                        } else {
                            if (isAdded()) {
                                Toast.makeText(requireContext(), "Error al eliminar el libro.", Toast.LENGTH_SHORT).show();
                            }
                            Log.e("EliminarLibro", "Error en la respuesta: " + response.code());
                        }
                        dismiss(); // Cierra el BottomSheet solo después de manejar el callback
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        if (isAdded()) {
                            Toast.makeText(requireContext(), "Error al conectar con el servidor.", Toast.LENGTH_SHORT).show();
                        }
                        Log.e("EliminarLibro", "Error al conectar con el servidor.", t);
                        dismiss(); // Cierra el BottomSheet solo después de manejar el callback
                    }
                });
            } else {
                Toast.makeText(requireContext(), "Parámetro no válido.", Toast.LENGTH_SHORT).show();
                dismiss(); // Cierra el BottomSheet aquí si el parámetro es inválido
            }
        });

        return view;
    }
}
