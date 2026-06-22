package com.example.progettototem;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView; // IMPORTANTE: usa TextView
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ProdottiAdapter extends RecyclerView.Adapter<ProdottiAdapter.ViewHolder> {
    private List<Prodotto> listaProdotti;
    private Context context;

    public ProdottiAdapter(Context context, List<Prodotto> lista) {
        this.context = context;
        this.listaProdotti = lista;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Prodotto p = listaProdotti.get(position);
        holder.txt.setText(p.nome + " - €" + String.format("%.2f", p.prezzo));

        // Il click funziona comunque anche sulla TextView!
        holder.txt.setOnClickListener(v -> {
            Intent intent = new Intent(context, DettagliActivity.class);
            intent.putExtra("NOME", p.nome);
            intent.putExtra("PREZZO", p.prezzo);
            intent.putExtra("DESC", p.descrizione);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() { return listaProdotti.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt; // Abbiamo cambiato btn con txt
        public ViewHolder(View itemView) {
            super(itemView);
            // simple_list_item_1 è di per sé una TextView, quindi il cast corretto è questo
            txt = (TextView) itemView;
        }
    }
}