package com.example.cowboyspacesbooks.vista;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cowboyspacesbooks.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BarraInferiorGestionNotas extends BottomSheetDialogFragment {
    public interface OnOptionSelectedListener {
        void onOptionSelected(String option, int imageResId);
    }

    private OnOptionSelectedListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnOptionSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnOptionSelectedListener");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflar el diseño del menú desplegable
        View view = inflater.inflate(R.layout.activity_barra_inferior_gestion_notas, container, false);


        TextView contentBook = view.findViewById(R.id.content_book);
        TextView resumeOption = view.findViewById(R.id.resume_option);
        TextView questionOption = view.findViewById(R.id.question_option);
        TextView thinkingOption = view.findViewById(R.id.thinking_option);

        contentBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onOptionSelected("Contenido del libro",R.drawable.ic_bookmark);
                dismiss();
            }
        });

        resumeOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onOptionSelected("Resumen",R.drawable.ic_document);
                dismiss();
            }
        });

        questionOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onOptionSelected("Pregunta", R.drawable.ic_question);
                dismiss();
            }
        });

        thinkingOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onOptionSelected("Pensamiento", R.drawable.ic_lightbulb);
                dismiss();
            }
        });
        return view;
    }
}