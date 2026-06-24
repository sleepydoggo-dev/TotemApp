package com.example.progettototem;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class StoricoAdapter extends RecyclerView.Adapter<StoricoAdapter.ViewHolder> {
    private final List<Ordine> lista;

    public StoricoAdapter(List<Ordine> lista) {
        this.lista = lista;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ordine, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ordine o = lista.get(position);
        holder.tData.setText(o.getData());
        holder.tTotale.setText("€ " + String.format("%.2f", o.getTotale()));

        StringBuilder sb = new StringBuilder();
        for (ProdottoOrdinato po : o.getProdotti()) {
            sb.append(po.getQuantita()).append("x ").append(po.getProdotto().getNome()).append(", ");
        }
        String prodottiStr = sb.toString();
        if (prodottiStr.endsWith(", ")) {
            prodottiStr = prodottiStr.substring(0, prodottiStr.length() - 2);
        }
        holder.tProdotti.setText(prodottiStr);
    }

    @Override
    public int getItemCount() { return lista.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tData, tTotale, tProdotti;
        public ViewHolder(View itemView) {
            super(itemView);
            tData = itemView.findViewById(R.id.textDataOrdine);
            tTotale = itemView.findViewById(R.id.textTotaleOrdine);
            tProdotti = itemView.findViewById(R.id.textProdottiOrdine);
        }
    }
}