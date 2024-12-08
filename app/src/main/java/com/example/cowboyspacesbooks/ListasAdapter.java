
package com.example.cowboyspacesbooks;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cowboyspacesbooks.modelo.Listas;

import java.util.ArrayList;
import java.util.List;

public class ListasAdapter extends RecyclerView.Adapter<ListasAdapter.ListasViewHolder> {

    private List<Listas> listas;
    private boolean customRadius;
    private Context context;
    private OnItemClickListener listener;

    public ListasAdapter(Context context, List<Listas> listas, boolean customRadius) {
        this.context = context;
        this.listas = listas;
        this.customRadius=customRadius;
    }
    public List<Listas> getSelectedListas() {
        List<Listas> selectedListas = new ArrayList<>();
        for (Listas lista : listas) {
            if (lista.isSelected()) {
                selectedListas.add(lista);
            }
        }
        return selectedListas;
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

        if (lista.isSelected()) {
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.appColor));
        } else {
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.white));
        }

        // Configura el evento onClick
        holder.itemView.setOnClickListener(v -> {
            // Alterna el estado de selección
            lista.setSelected(!lista.isSelected());
            notifyItemChanged(position);
            //iniciar una nueva actividad (situacional)
            if (listener != null) {
                listener.onItemClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listas.size();
    }

    public class ListasViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private ImageView imageView;
        private TextView titleTextView, countTextView;

        public ListasViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
            imageView = itemView.findViewById(R.id.iv_main_image);
            titleTextView = itemView.findViewById(R.id.tv_coleccion_title);
            countTextView = itemView.findViewById(R.id.tv_book_count);
        }

        public void bind(Listas lista) {
            //Portada
            /*Glide.with(context)
                    .load(lista.getPortada())
                    .placeholder(R.drawable.lo_cargando)
                    .error(R.drawable.ic_book_placeholder)
                    .into(imageView);*/
            titleTextView.setText(lista.getNameList());
            Log.d("ListasAdapter", lista.getNameList());
        }
    }
}

