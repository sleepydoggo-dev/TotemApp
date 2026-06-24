package com.example.progettototem;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
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
        holder.tTotale.setText(holder.itemView.getContext().getString(R.string.price_format, o.getTotale()));

        StringBuilder sb = new StringBuilder();
        for (ProdottoOrdinato po : o.getProdotti()) {
            sb.append(holder.itemView.getContext().getString(R.string.order_item_qty_format, po.getQuantita(), po.getProdotto().getNome())).append(", ");
        }
        String prodottiStr = sb.toString();
        if (prodottiStr.endsWith(", ")) {
            prodottiStr = prodottiStr.substring(0, prodottiStr.length() - 2);
        }
        holder.tProdotti.setText(holder.itemView.getContext().getString(R.string.order_items_format, prodottiStr));

        holder.btnRifai.setOnClickListener(v -> {
            for (ProdottoOrdinato po : o.getProdotti()) {
                Carrello.getInstance().aggiungiProdotto(po.getProdotto(), po.getQuantita(), v.getContext());
            }
            Toast.makeText(v.getContext(), v.getContext().getString(R.string.prodotti_aggiunti), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() { return lista.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tData, tTotale, tProdotti;
        Button btnRifai;
        public ViewHolder(View itemView) {
            super(itemView);
            tData = itemView.findViewById(R.id.textDataOrdine);
            tTotale = itemView.findViewById(R.id.textTotaleOrdine);
            tProdotti = itemView.findViewById(R.id.textProdottiOrdine);
            btnRifai = itemView.findViewById(R.id.btnRifaiOrdine);
        }
    }
}