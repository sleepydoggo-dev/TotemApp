package com.example.progettototem;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProdottiAdapter extends RecyclerView.Adapter<ProdottiAdapter.ViewHolder> {
    private final List<Prodotto> listaProdotti;
    private final Context context;
    private final Map<Integer, Integer> quantitaMap = new HashMap<>();

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
        
        if (!quantitaMap.containsKey(position)) {
            quantitaMap.put(position, 1);
        }

        holder.tNome.setText(p.nome);
        holder.tDesc.setText(p.descrizione);
        holder.tPrezzo.setText("€ " + String.format("%.2f", p.prezzo));
        holder.tQuant.setText(String.valueOf(quantitaMap.get(position)));

        holder.btnPiu.setOnClickListener(v -> {
            int q = quantitaMap.get(position) + 1;
            quantitaMap.put(position, q);
            holder.tQuant.setText(String.valueOf(q));
        });

        holder.btnMeno.setOnClickListener(v -> {
            int q = quantitaMap.get(position);
            if (q > 1) {
                q--;
                quantitaMap.put(position, q);
                holder.tQuant.setText(String.valueOf(q));
            }
        });

        holder.btnAggiungi.setOnClickListener(v -> {
            int q = quantitaMap.get(position);
            Carrello.getInstance().aggiungiProdotto(p, q, context);
            Toast.makeText(context, p.nome + " aggiunto!", Toast.LENGTH_SHORT).show();
            quantitaMap.put(position, 1);
            holder.tQuant.setText("1");
        });

        holder.tDettagli.setOnClickListener(v -> {
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
        TextView tNome, tDesc, tPrezzo, tQuant, tDettagli;
        Button btnPiu, btnMeno, btnAggiungi;

        public ViewHolder(View itemView) {
            super(itemView);
            tNome = itemView.findViewById(R.id.nomeProdotto);
            tDesc = itemView.findViewById(R.id.descProdotto);
            tPrezzo = itemView.findViewById(R.id.prezzoProdottoItem);
            tQuant = itemView.findViewById(R.id.textQuantitaItem);
            tDettagli = itemView.findViewById(R.id.btnDettagliItem);
            btnPiu = itemView.findViewById(R.id.btnPiuItem);
            btnMeno = itemView.findViewById(R.id.btnMenoItem);
            btnAggiungi = itemView.findViewById(R.id.btnAggiungiItem);
        }
    }
}
