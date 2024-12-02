package com.example.cowboyspacesbooks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cowboyspacesbooks.modelo.Notes;

import org.w3c.dom.Text;

import java.util.List;

public class NotasAdapter extends RecyclerView.Adapter<NotasAdapter.NotaViewHolder> {

    private List<Notes> notasList; // Lista de notas
    private Context context;
    private OnItemClickListener listener;

    public NotasAdapter(Context context, List<Notes> notasList) {
        this.context = context;
        this.notasList = notasList;
    }

    // Interface para manejar clics en cada ítem
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public NotaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_nota, parent, false);
        return new NotaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotaViewHolder holder, int position) {
        Notes nota = notasList.get(position);
        holder.bind(nota);
    }

    @Override
    public int getItemCount() {
        return notasList.size();
    }

    public class NotaViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitulo;
        private TextView tvAutor;
        private TextView tvContenido;
        private TextView tvFecha;
        private TextView tvPaginas;
        private TextView tvTipoNota;
        private ImageView ivNotaPortada;

        public NotaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitulo = itemView.findViewById(R.id.tv_title);
            tvAutor = itemView.findViewById(R.id.tv_author);
            tvContenido = itemView.findViewById(R.id.tv_cuerpo_nota);
            tvFecha = itemView.findViewById(R.id.tv_fecha);
            tvPaginas = itemView.findViewById(R.id.tv_pages);
            tvTipoNota = itemView.findViewById(R.id.tv_tipo_nota);
            ivNotaPortada = itemView.findViewById(R.id.iv_note_image);

            // Agrega un listener para clics en cada ítem
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
            });
        }

        public void bind(Notes nota) {
            tvTitulo.setText(nota.getTitulo());
            tvAutor.setText(nota.getAutor());
            tvContenido.setText(nota.getCuerpo());
            tvFecha.setText(nota.getFecha());
            tvTipoNota.setText(nota.getTipoNota());
            tvPaginas.setText("Pág. " + nota.getPagInicio() + " - " + nota.getPagFinal());

            Glide.with(context)
                    .load(nota.getImagenUrl())
                    .placeholder(R.drawable.lo_cargando)
                    .error(R.drawable.error_image)
                    .into(ivNotaPortada);
        }
        }
    }

