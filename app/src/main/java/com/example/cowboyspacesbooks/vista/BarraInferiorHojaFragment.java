package com.example.cowboyspacesbooks.vista;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cowboyspacesbooks.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BarraInferiorHojaFragment extends BottomSheetDialogFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_barra_inferior_gestion_libros, container, false);

        // Configura los elementos de la vista
        TextView editOption = view.findViewById(R.id.edit_option);
        TextView changeStatusOption = view.findViewById(R.id.change_status_option);
        TextView deleteOption = view.findViewById(R.id.delete_option);

        editOption.setOnClickListener(v -> {
            // Acción para editar el libro
            dismiss(); // Cierra el BottomSheet
        });

        changeStatusOption.setOnClickListener(v -> {
            // Acción para cambiar el estado del libro
            dismiss();
        });

        deleteOption.setOnClickListener(v -> {
            // Acción para eliminar el libro
            dismiss();
        });

        return view;
    }
}
