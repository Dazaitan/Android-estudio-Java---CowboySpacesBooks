
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
import com.example.cowboyspacesbooks.modelo.Listas;

import java.util.List;

public class ListasAdapter extends RecyclerView.Adapter<ListasAdapter.ListasViewHolder> {

    private List<Listas> listas;
    private Context context;
    private OnItemClickListener listener;

    public ListasAdapter(Context context, List<Listas> listas) {
        this.context = context;
        this.listas = listas;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ListasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_coleccion, parent, false);
        return new ListasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListasViewHolder holder, int position) {
        Listas lista = listas.get(position);
        holder.bind(lista);
    }

    @Override
    public int getItemCount() {
        return listas.size();
    }

    public class ListasViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView titleTextView, countTextView;

        public ListasViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_main_image);
            titleTextView = itemView.findViewById(R.id.tv_coleccion_title);
            countTextView = itemView.findViewById(R.id.tv_book_count);

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
            });
        }

        public void bind(Listas lista) {
            Glide.with(context)
                    .load(lista.getPortada())
                    .placeholder(R.drawable.lo_cargando)
                    .error(R.drawable.ic_book_placeholder)
                    .into(imageView);
            titleTextView.setText(lista.getNameList());
        }
    }
}

