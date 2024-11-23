package com.example.cowboyspacesbooks;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import com.bumptech.glide.Glide;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cowboyspacesbooks.modelo.Book;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    private List<Book> bookList;
    private Context context;
    private OnItemClickListener listener;

    public BookAdapter(Context context, List<Book> bookList) {
        this.context = context;
        this.bookList = bookList;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_libro, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = bookList.get(position);
        holder.titleTextView.setText(book.getTitulo());
        holder.autorPublisherTextView.setText(book.getAutor());
        holder.bind(book);
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView titleTextView;
        private TextView autorPublisherTextView;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.book_cover);
            titleTextView = itemView.findViewById(R.id.book_title);
            autorPublisherTextView =itemView.findViewById(R.id.tv_author_and_publisher);

            // Agrega el listener para capturar el clic
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }

        public void bind(Book book) {
            // Carga inicial de la imagen
            Glide.with(context)
                    .load(book.getCoverImageUrl())
                    .placeholder(R.drawable.lo_cargando)
                    .error(R.drawable.error_image)
                    .into(imageView);

            // Configura el título
            titleTextView.setText(book.getTitulo());
            autorPublisherTextView.setText(book.getAutor());
        }
    }
}
