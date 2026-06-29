package com.example.progettototem;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

public class ProdottiAdapter extends RecyclerView.Adapter<ProdottiAdapter.ViewHolder> {
    private final List<Prodotto> listaProdotti;
    private final Context context;

    public ProdottiAdapter(Context context, List<Prodotto> lista) {
        this.context = context;
        this.listaProdotti = lista;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_prodotto, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Prodotto p = listaProdotti.get(position);

        holder.tNome.setText(p.getNome());

        // Caricamento ottimizzato con Glide per eliminare il lag
        String imgKey = p.getImmagineKey();
        int imgRes = 0;
        if (imgKey != null && !imgKey.isEmpty()) {
            imgRes = context.getResources().getIdentifier(imgKey, "drawable", context.getPackageName());
        }
        
        // Fallback a burger se l'immagine non viene trovata
        if (imgRes == 0) {
            imgRes = R.drawable.burger;
        }

        Glide.with(context)
                .load(imgRes)
                .placeholder(R.drawable.burger)
                .centerCrop()
                .into(holder.imgProdotto);

        // Click sull'intero blocco (Apre DettagliActivity)
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DettagliActivity.class);
            intent.putExtra("NOME", p.getNome());
            intent.putExtra("PREZZO", p.getPrezzo());
            intent.putExtra("DESC", p.getDescrizione());
            intent.putExtra("IMG", p.getImmagineKey());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() { return listaProdotti.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tNome;
        ImageView imgProdotto;

        public ViewHolder(View itemView) {
            super(itemView);
            tNome = itemView.findViewById(R.id.nomeProdotto);
            imgProdotto = itemView.findViewById(R.id.imgProdottoItem);
        }
    }
}