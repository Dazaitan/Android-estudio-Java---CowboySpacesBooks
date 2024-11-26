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

import com.example.cowboyspacesbooks.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

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
            // Acción para eliminar el libro
            dismiss();
        });
        return view;
    }
}
