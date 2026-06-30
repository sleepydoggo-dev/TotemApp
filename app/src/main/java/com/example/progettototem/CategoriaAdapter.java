/* package com.example.progettototem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CategoriaAdapter extends RecyclerView.Adapter<CategoriaAdapter.ViewHolder> {
    private final List<String> categorie;
    private final Context context;
    private final OnCategoriaClickListener listener;
    private int selectedPosition = 0;

    public interface OnCategoriaClickListener {
        void onCategoriaClick(String categoria);
    }

    public CategoriaAdapter(Context context, List<String> categorie, OnCategoriaClickListener listener) {
        this.context = context;
        this.categorie = categorie;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_categoria, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String cat = categorie.get(position);
        holder.tNome.setText(cat);

        // Gestisce l'effetto "Attivo" (il background cambia colore se selezionata)
        holder.itemView.setActivated(selectedPosition == position);

        holder.itemView.setOnClickListener(v -> {
            int previousPos = selectedPosition;
            selectedPosition = holder.getAdapterPosition();
            notifyItemChanged(previousPos);
            notifyItemChanged(selectedPosition);
            listener.onCategoriaClick(cat);
        });
    }

    @Override
    public int getItemCount() {
        return categorie.size();
    }

    public void setSelection(int position) {
        int previousPos = selectedPosition;
        selectedPosition = position;
        notifyItemChanged(previousPos);
        notifyItemChanged(selectedPosition);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tNome;

        public ViewHolder(View itemView) {
            super(itemView);
            tNome = itemView.findViewById(R.id.nomeCategoria);
        }
    }
} */