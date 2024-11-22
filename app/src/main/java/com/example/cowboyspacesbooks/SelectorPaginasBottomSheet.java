package com.example.cowboyspacesbooks;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class SelectorPaginasBottomSheet extends BottomSheetDialogFragment {

    private static final String ARG_TOTAL_PAGINAS = "total_paginas"; // Constante para las páginas totales
    private int totalPaginas;

    // Interface para comunicar la selección
    public interface AlSeleccionarPaginaListener {
        void onPaginaSeleccionada(int paginaSeleccionada);
    }

    private AlSeleccionarPaginaListener listener;

    // Método estático para crear una nueva instancia del BottomSheet con las páginas totales
    public static SelectorPaginasBottomSheet nuevaInstancia(int totalPaginas) {
        SelectorPaginasBottomSheet fragmento = new SelectorPaginasBottomSheet();
        Bundle args = new Bundle();
        args.putInt(ARG_TOTAL_PAGINAS, totalPaginas);
        fragmento.setArguments(args);
        return fragmento;
    }

    @Override
    public void onAttach(@NonNull Context contexto) {
        super.onAttach(contexto);

        // Asegurarse de que la actividad implemente el listener
        if (contexto instanceof AlSeleccionarPaginaListener) {
            listener = (AlSeleccionarPaginaListener) contexto;
        } else {
            throw new RuntimeException(contexto.toString() + " debe implementar AlSeleccionarPaginaListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflador, @Nullable ViewGroup contenedor, @Nullable Bundle savedInstanceState) {
        return inflador.inflate(R.layout.activity_bottom_sheet_selector_paginas, contenedor, false); // Inflar el layout
    }

    @Override
    public void onViewCreated(@NonNull View vista, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(vista, savedInstanceState);

        // Recuperar el argumento con el número total de páginas
        if (getArguments() != null) {
            totalPaginas = getArguments().getInt(ARG_TOTAL_PAGINAS, 1);
        }

        // Configurar el NumberPicker
        NumberPicker selectorPaginas = vista.findViewById(R.id.selector_paginas);
        selectorPaginas.setMinValue(1);
        selectorPaginas.setMaxValue(totalPaginas);
        selectorPaginas.setWrapSelectorWheel(false);

        // Botón de confirmación
        Button btnConfirmar = vista.findViewById(R.id.btn_confirmar);
        btnConfirmar.setOnClickListener(v -> {
            int paginaSeleccionada = selectorPaginas.getValue();
            if (listener != null) {
                listener.onPaginaSeleccionada(paginaSeleccionada); // Enviar la página seleccionada al listener
            }
            dismiss(); // Cerrar el BottomSheet
        });
    }
}
